package ku.cs.services;

import javafx.application.Platform;
import ku.cs.controllers.admin.AdminDashboardPageController;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class FileWatcher implements Runnable {
    private static final Object FILE_LOCK = new Object();
    private final AdminDashboardPageController dataPage;
    private final Map<Path, Set<String>> directoriesToWatch;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private WatchService watchService;

    public FileWatcher(AdminDashboardPageController dataPage, Set<String> csvFilePaths) {
        this.dataPage = dataPage;
        this.directoriesToWatch = new HashMap<>();
        for (String filePath : csvFilePaths) {
            Path path = Paths.get(filePath);
            Path dir = path.getParent().toAbsolutePath().normalize();
            String fileName = path.getFileName().toString();
            directoriesToWatch.computeIfAbsent(dir, k -> new HashSet<>()).add(fileName);
        }
    }

    @Override
    public void run() {
        try {
            watchService = FileSystems.getDefault().newWatchService();
            for (Path dir : directoriesToWatch.keySet()) {
                dir.register(watchService, ENTRY_MODIFY);
            }

            while (running.get()) {
                WatchKey key;
                try {
                    key = watchService.take();
                } catch (ClosedWatchServiceException e) {
                    break;
                }

                Path dir = (Path) key.watchable();

                Set<String> filesInDir = directoriesToWatch.get(dir.toAbsolutePath().normalize());

                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == ENTRY_MODIFY) {
                        String fileName = event.context().toString();

                        if (filesInDir != null && filesInDir.contains(fileName)) {
                            updateDataFromCSV();
                        }
                    }
                }
                if (!key.reset()) {
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error initializing or running FileWatcher", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            try {
                if (watchService != null) {
                    watchService.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("WatchService closed while still running; this is expected behavior when using more than one application instance.", e);
            }
        }
    }

    public void stopWatching() {
        running.set(false);
        try {
            if (watchService != null) {
                watchService.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error closing WatchService in stopWatching()", e);
        }
    }

    private void updateDataFromCSV() {
        synchronized (FILE_LOCK) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            DataProvider.getDataProvider().updateAdminData();

            Platform.runLater(dataPage::updateData);
        }
    }
}