<h1 align="center">
  <br>
    <span style="color: #3be8ac;">CS211 Project FormXpress</span>
  <br>
</h1>

<p align="center">
    <b>โปรเจกต์นี้เป็นส่วนหนึ่งของ รายวิชา 01418211 - การสร้างซอฟต์แวร์ (Software Construction)</b> <br>
    <b>ภาคต้น ปีการศึกษา 2567</b> <br>
</p>


## ชื่อทีม get-grade-a

### สมาชิกในทีม
| รหัสนิสิต  | ชื่อ-นามสกุล (ชื่อเล่น)        | GitHub username |
|------------|--------------------------------|-----------------|
| 6610401993 | ฐาพล ชินกรสกุล (ซัน) | sunmodza        |
| 6610402116 | ธีร์ อนุสรณ์ศาสน์ (ตี้)    | TeeAnusonsart |
| 6610402272 | อิสรพงษ์ เถื่อนสกุล (เกม)  | Gamenakub       |
| 6610405832 | จิรัฏฐ์ ค่องสกุล (เฟม)     | 4Aim            |

## คลิปความก้าวหน้าของระบบ
| ครั้งที่                      |       กำหนดส่ง        | Youtube Link |
|-------------------------------|:---------------------:|--------------|
| ความก้าวหน้าของระบบครั้งที่ 1 | 9 ส.ค. 2567 17:00 น.  | https://youtu.be/7DHbP0pETzA?si=2GtJZ2wo1RFebmbg |
| ความก้าวหน้าของระบบครั้งที่ 2 | 6 ก.ย. 2567 17:00 น.  | https://youtu.be/y2gTjFuIMzY?si=rd1wRr-JimpZvbcf |
| ความก้าวหน้าของระบบครั้งที่ 3 | 27 ก.ย. 2567 17:00 น. | https://youtu.be/Ra9TADI_Kwg |
| โครงงานที่สมบูรณ์             | 18 ต.ค. 2567 17:00 น. | https://youtu.be/puubVeLjLiI |

## สรุปสิ่งที่พัฒนาในแต่ละครั้ง
### ความก้าวหน้าของระบบครั้งที่ 1
1. ฐาพล ชินกรสกุล (ซัน)
   * ออกแบบ figma https://www.figma.com/design/UAJjepWh926qyFaaLjDQHf/Get-Grade-A?node-id=0-1&t=oX0ypJjhFkDXIzBp-1
   * ออกแบบ Table component สำหรับการแสดง table ทั้งโปรแกรม
   * ออกระบบ Popup ทั้ง PopupComponent และ BasePopup สำหรับการใช้งาน popup ทั้งโปรแกรม
   * ออกแบบและสร้าง FXML สำหรับหน้าแสดงและการจัดการคำร้องของเจ้าหน้าที่คณะ ซึ่งอยู่ในส่วนของระบบสำหรับเจ้าหน้าที่คณะ และเชื่อมต่อกับ Controller Class
   * ออกแบบและสร้าง FXML สำหรับหน้าแสดงและการจัดการคำร้องของเจ้าหน้าที่ภาควิชา  ซึ่งอยู่ในส่วนของระบบสำหรับเจ้าหน้าที่ภาควิชา และเชื่อมต่อกับ Controller Class
   * ออกแบบและสร้าง FXML สำหรับหน้าการจัดการข้อมูลนิสิตในภาควิชา ซึ่งอยู่ในส่วนของระบบสำหรับเจ้าหน้าที่ภาควิชา และเชื่อมต่อกับ Controller Class
2. ธีร์ อนุสรณ์ศาสน์ (ตี้)
   * ออกแบบ figma https://www.figma.com/design/UAJjepWh926qyFaaLjDQHf/Get-Grade-A?node-id=0-1&t=oX0ypJjhFkDXIzBp-1
   * ออกแบบและสร้าง FXML สำหรับหน้าแสดงรายชื่อนิสิตในที่ปรึกษา ซึ่งอยู่ในส่วนของระบบสำหรับอาจารย์ที่ปรึกษา และเชื่อมต่อกับ Controller Class
   * ออกแบบและสร้าง FXML สำหรับหน้าแสดงประวัติรายการคำร้องของนิสิตในที่ปรึกษา ซึ่งอยู่ในส่วนของระบบสำหรับอาจารย์ที่ปรึกษา และเชื่อมต่อกับ Controller Class
   * ออกแบบและสร้าง FXML สำหรับหน้าจัดการรายการคำร้องของนิสิตในที่ปรึกษา ซึ่งอยู่ในส่วนของระบบสำหรับอาจารย์ที่ปรึกษา และเชื่อมต่อกับ Controller Class
   * ออกแบบและสร้าง FXML สำหรับหน้า Dashboard ซึ่งอยู่ในส่วนของระบบสำหรับผู้ดูแลระบบ และเชื่อมต่อกับ Controller Class
3. อิสรพงษ์ เถื่อนสกุล (เกม)
   * ออกแบบ figma https://www.figma.com/design/UAJjepWh926qyFaaLjDQHf/Get-Grade-A?node-id=0-1&t=oX0ypJjhFkDXIzBp-1
   * ออกแบบ style "main-style.css" เบื้องต้น
   * ออกแบบและสร้าง FXML ระบบของผู้ใช้ระบบ (ทุกบทบาท) "ระบบการลงชื่อเข้าใช้งาน (Login)" และเชื่อมต่อกับ Controller Class
   * ออกแบบและสร้าง FXML ระบบของผู้ใช้ระบบ (ทุกบทบาท) "ระบบการจัดการข้อมูลส่วนตัวของผู้ใช้ระบบ" และเชื่อมต่อกับ Controller Class
   * ออกแบบและสร้าง FXML ระบบของผู้ดูแลระบบ "ระบบการจัดการข้อมูลผู้ใช้" และเชื่อมต่อกับ Controller Class
   * ออกแบบและสร้าง FXML ระบบของผู้ดูแลระบบ "ระบบการจัดการข้อมูลคณะ และข้อมูลภาควิชา" และเชื่อมต่อกับ Controller Class
   * ออกแบบและสร้าง FXML ระบบของผู้ดูแลระบบ "การจัดการข้อมูลเจ้าหน้าที่คณะ เจ้าหน้าที่ภาควิชา และอาจารย์ที่ปรึกษา" และเชื่อมต่อกับ Controller Class
   * ออกแบบและสร้าง FXML ระบบของนิสิต "การลงทะเบียนเข้าใช้งาน" และเชื่อมต่อกับ Controller Class
   * ออกแบบและสร้าง FXML ของ "GUI ที่แสดงถึงข้อมูลนิสิตผู้จัดทําโปรแกรม" และเชื่อมต่อกับ Controller Class
4. จิรัฏฐ์ ค่องสกุล (เฟม)
   * ออกแบบ figma https://www.figma.com/design/UAJjepWh926qyFaaLjDQHf/Get-Grade-A?node-id=0-1&t=oX0ypJjhFkDXIzBp-1
   * ออกแบบและสร้าง FXML ระบบของนิสิต “ระบบการติดตามคำร้อง” และเชื่อมต่อกับ Controller Class
   * ออกแบบและสร้าง FXML ระบบของนิสิต “ระบบการสร้างใบคำร้อง” และเชื่อมต่อกับ Controller Class
   * ออกแบบและสร้าง FXML ของ “ใบคำร้องการลงทะเบียนเรียนล่าช้า” และเชื่อมต่อกับ Controller Class
   * ออกแบบและสร้าง FXML ของ “ใบคำร้องการถอนรายวิชาล่าช้า” และเชื่อมต่อกับ Controller Class
   * ออกแบบและสร้าง FXML ของ “ใบคำร้องการลงทะเบียนเรียนร่วม” และเชื่อมต่อกับ Controller Class
   * ออกแบบและสร้าง FXML ของ “ใบคำร้องขอลากิจหรือลาป่วย” และเชื่อมต่อกับ Controller Class
   * ออกแบบและสร้าง FXML ระบบของเจ้าหน้าที่ภาควิชา “ระบบการจัดการผู้อนุมัติคำร้อง” และเชื่อมต่อกับ Controller Class
   * ออกแบบและสร้าง FXML ระบบของเจ้าหน้าที่คณะ “ระบบการจัดการผู้อนุมัติคำร้อง” และเชื่อมต่อกับ Controller Class


### ความก้าวหน้าของระบบครั้งที่ 2
1. ฐาพล ชินกรสกุล (ซัน)
   * ใส่ fxid และเชื่อม css กับ FXML ที่รับผิดชอบ (อ้างอิงจากความก้าวหน้าของระบบครั้งที่ 1)
   * ประกาศตัวแปรที่สัมพันธ์กับ fxid ใน Controller Class ที่รับผิดชอบ (อ้างอิงจากความก้าวหน้าของระบบครั้งที่ 1)
   * สร้าง Model คลาส Officer, DepartmentOfficer และ FacultyOfficer
   * สร้าง Collection คลาส OfficerList, DepartmentOfficerList และ FacultyOfficerList
2. ธีร์ อนุสรณ์ศาสน์ (ตี้)
   * ใส่ fxid และเชื่อม css กับ FXML ที่รับผิดชอบ (อ้างอิงจากความก้าวหน้าของระบบครั้งที่ 1)
   * ประกาศตัวแปรที่สัมพันธ์กับ fxid ใน Controller Class ที่รับผิดชอบ (อ้างอิงจากความก้าวหน้าของระบบครั้งที่ 1)
   * สร้างคลาส Advisor,RequestForm,AddDropForm
   * สร้างคลาส AdvisorList
3. อิสรพงษ์ เถื่อนสกุล (เกม)
   * ใส่ fxid และเชื่อม css กับ FXML ที่รับผิดชอบ (อ้างอิงจากความก้าวหน้าของระบบครั้งที่ 1)
   * ประกาศตัวแปรที่สัมพันธ์กับ fxid ใน Controller Class ที่รับผิดชอบ (อ้างอิงจากความก้าวหน้าของระบบครั้งที่ 1)
   * สร้างคลาส User, Faculty และ Department
   * สร้างคลาส UserList, FacultyList และ DepartmentList
   * ปรับปรุง แก้ไข และพัฒนา style "main-style.css"
4. จิรัฏฐ์ ค่องสกุล (เฟม)
   * ใส่ fxid และเชื่อม css กับ FXML ที่รับผิดชอบ (อ้างอิงจากความก้าวหน้าของระบบครั้งที่ 1)
   * ประกาศตัวแปรที่สัมพันธ์กับ fxid ใน Controller Class ที่รับผิดชอบ (อ้างอิงจากความก้าวหน้าของระบบครั้งที่ 1)
   * สร้างคลาส Student, AbsenceRequestForm, CoEnrollRequestForm, DepartmentApprover, FacultyApprover และ Subject
   * สร้างคลาส StudentList, RequestFormList, DepartmentApproverList, FacultyApproverList และ SubjectList

### ความก้าวหน้าของระบบครั้งที่ 3
1. ฐาพล ชินกรสกุล (ซัน)
   * สร้างคลาสและ interface ใน datahandle ทั้งหมด ซึ่งเป็นคลาสในการจัดการอ่านเขียนข้อมูลทั้งหมดกับ CSV ในโปรแกรม ซึ่ง DataSource ทุกตัวที่อ่านเขียนไฟล์จะสืบทอดมาจาก interface ในนี้
   * ปรับปรุง Table โดยทำให้สร้าง table ได้ด้วย TableDescriptor เพิ่มความยืดหยุ่นและความง่ายต่อการสร้าง Table และทำให้ Table มีการรับส่ง event ได้ และสร้าง interface ที่ใช้กับ table ทั้งหมด
   * ปรับปรุง Table ให้สามารถทำการ sort ได้โดยง่าย สามารถกำหนด Comparator ให้ TableColumn ได้
   * ทำให้ระบบของเจ้าหน้าที่คณะ สามารถจัดการกับคำร้องที่ถูกส่งมาจากภาควิชาได้ เช่น อนุมัติคำขอ หรือปฏิเสธคำขอ
   * ทำให้ระบบของเจ้าหน้าที่คณะ สามารถจัดการกับข้อมูลผู้อนุมัติระดับคณะได้
   * ทำให้ระบบของเจ้าหน้าที่ภาควิชา สามารถจัดการกับคำร้องที่ถูกส่งมาจากอาจารย์ที่ปรึกษาได้ เช่น อนุมัติคำขอส่งต่อให้เจ้าหน้าที่ภาควิชา หรือปฏิเสธคำขอ
   * ทำให้ระบบของเจ้าหน้าที่ภาควิชา สามารถจัดการกับข้อมูลนิสิตในคณะได้ ได้แก่ การเพิ่มข้อมูลนิสิต การแก้ไขข้อมูลนิสิต และการกำหนดอาจารย์ที่ปรึกษา
   * ทำให้ระบบของเจ้าหน้าที่ภาควิชา สามารถจัดการกับข้อมูลผู้อนุมัติระดับภาควิชาได้
2. ธีร์ อนุสรณ์ศาสน์ (ตี้)
   * ทำให้ระบบของอาจารย์ที่ปรึกษา สามารถจัดการกับคำร้องที่ถูกส่งมาจาก student ได้
   * ทำให้ระบบของอาจารย์ที่ปรึกษา สามารถดูข้อมูลของนิสิตในที่ปรึกษาได้ รวมถึงสามารถดูประวัติคำร้องของนิสิต คนนั้นๆได้
   * ทำหน้า Dashboard สำหรับแสดงข้อมูลโดยรวมของระบบ
   * สร้าง Data Provider สำหรับเตรียมข้อมูลทุกอย่างที่ระบบต้องใช้งาน เช่นข้อมูลสำหรับล็อคอิน ข้อมูลสำหรับผู้ใช้งาน รวมถึงการเซฟข้อมูล
   * ทำ Session สำหรับเก็บ User ที่ล็อคอินเข้าใช้งาน
   * ทำ css ให้กับ GUI ของระบบ รวมไปถึง css สำหรับเปลี่ยน theme และ font
   * ทำ Theme provider สำหรับการจัดการเกี่ยวกับการเปลี่ยน theme และการ set theme ให้ GUI ในแต่ละหน้า
   * เพิ่มปุ่มพื้นฐาน เช่น ปุ่มเปลี่ยน theme font ให้กับ navigation bar
   * ทำ Search Controller สำหรับรองรับการนำไปใช้ในการค้นหาข้อมูลภายในตาราง
3. อิสรพงษ์ เถื่อนสกุล (เกม)
   * ทำให้หน้า user-login สามารถรับ username และ password เพื่อนำไปหา user ได้
   * ทำให้ระบบบังคับเปลี่ยนรหัสผ่านในการใช้งานครั้งแรกได้
   * ทำให้หน้า student-register สามารถรับค่า และเช็คกับข้อมูลนิสิตในระบบได้
   * ทำให้ผู้ใช้สามารถเปลี่ยนรหัสผ่านได้
   * ทำให้ผู้ใช้สามารถเปลี่ยนรูปโปรไฟล์ และครอบตัดไฟล์รูปภาพเป็นจัตุรัสได้
   * ทำให้สามารถค้นหาและคัดกรองข้อมูลให้กับตารางที่รับผิดชอบ (อ้างอิงจากความก้าวหน้าของระบบครั้งที่ 1)
   * ทำให้หน้าจอหลัก ตาราง และป๊อปอัพ สามารถแสดงผลร่วมกับระบบเปลี่ยนธีมได้
   * จัดการ exception และข้อผิดพลาดต่าง ๆ โดยการแจ้งให้ user ทราบ ด้วย AlertService ใน Controller ส่วนที่รับผิดชอบ (อ้างอิงจากความก้าวหน้าของระบบครั้งที่ 1)
4. จิรัฏฐ์ ค่องสกุล (เฟม)
   * ทำให้หน้าติดตามคำร้องแสดงใบคำร้องทั้งหมดที่สร้างขึ้นมาได้ โดยแสดงรายละเอียดต่างๆ และยังสามารถดูใบคำร้องที่สร้างไปแล้วได้อีกด้วย
   * ทำให้สามารถกรองคำร้องในหน้าติดตามใบคำร้องด้วยสถานะคำร้องต่างๆ ที่ต้องการได้
   * ทำให้สามารถค้นหาใบคำร้องโดยการใส่ข้อมูลส่วนหนึ่งของหัวเรื่อง หรือประเภทใบคำร้องได้
   * ทำให้หน้าสร้างใบคำร้องสร้าง RequestForm ตามประเภท แล้วส่งข้อมูลไปหน้าอื่นๆ เพื่อนำข้อมูลนั้นมาเซ็ตข้อความในหน้าใบคำร้องต่างๆ ได้
   * ทำให้สามารถใส่ข้อมูรายวิชา เพิ่มรายวิชา หรือลบรายวิชาในใบคำร้องบางประเภทได้อีกด้วย
   * สร้างหน้าแสดงตัวอย่างไฟล์ PDF ของใบคำร้องประเภทต่างๆ
   * ทำให้ผู้ใช้สามารถแปลงข้อมูลใบคำร้องเป็นไฟล์ PDF แล้วบันทึกลง data ได้ด้วย service RequestFormToPDF
   * สร้าง AlertService ไว้แสดงข้อความหรือข้อมูลที่ต้องการแจ้งให้ผู้ใช้ทราบ

### โครงงานที่สมบูรณ์
1. ฐาพล ชินกรสกุล (ซัน)
   * จัดทำ PDF วิธีการใช้งานระบบ
   * ตัดต่อวิดีโอนำเสนอโครงงานที่สมบูรณ์
   * จัดระเบียบให้กับโค้ด
   * แก้ไข ปรับปรุง และพัฒนา feature ที่เกี่ยวข้องกับระบบของผู้ใช้ และระบบของผู้ดูแลระบบ ให้ถูกต้องและสมบูรณ์
2. ธีร์ อนุสรณ์ศาสน์ (ตี้)
   * จัดทำ PDF วิธีการใช้งานระบบ
   * ตัดต่อวิดีโอนำเสนอโครงงานที่สมบูรณ์
   * จัดระเบียบให้กับโค้ด
   * แก้ไข ปรับปรุง และพัฒนา feature ที่เกี่ยวข้องกับระบบของผู้ใช้ และระบบของผู้ดูแลระบบ ให้ถูกต้องและสมบูรณ์
3. อิสรพงษ์ เถื่อนสกุล (เกม)
   * จัดทำ PDF วิธีการใช้งานระบบ
   * ตัดต่อวิดีโอนำเสนอโครงงานที่สมบูรณ์
   * จัดระเบียบให้กับโค้ด
   * แก้ไข ปรับปรุง และพัฒนา feature ที่เกี่ยวข้องกับระบบของผู้ใช้ และระบบของผู้ดูแลระบบ ให้ถูกต้องและสมบูรณ์
4. จิรัฏฐ์ ค่องสกุล (เฟม)
   * จัดทำ PDF วิธีการใช้งานระบบ 
   * ตัดต่อวิดีโอนำเสนอโครงงานที่สมบูรณ์ 
   * จัดระเบียบให้กับโค้ด 
   * แก้ไข ปรับปรุง feature ที่เกี่ยวข้องกับระบบของผู้ใช้ และระบบของผู้ดูแลระบบ ให้ถูกต้องและสมบูรณ์ 
   * พัฒนา feature การสร้างและการแสดงผลไฟล์ pdf ให้ทำงานได้ถูกต้องและสมบูรณ์
   * สร้างหน้าคำแนะนำการใช้งาน

## วิธีการติดตั้งและรันโปรแกรม
รวมถึงที่อยู่ของไฟล์ pdf
## Download
#### Jar file for Windows
>Download <b>FormXpress-for-Windows.zip</b> from [Release](<link>) or [Click](<link>)
#### Jar file for MacOs M1
>Download <b>FormXpress-for-Windows.zip</b> from [Release](<link>) or [Click](<link>)

## Installation[![](https://raw.githubusercontent.com/aregtech/areg-sdk/master/docs/img/pin.svg)](#introduction)
### Windows Installation
#### 1. Extract Zip File
<img width="300" alt="install1" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR2UOW09a8y-Ue_FtTFn01C4U4-dZmIax-P_g&s">

#### 2. Select <Name> Folder
<img width="300" alt="install1" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR2UOW09a8y-Ue_FtTFn01C4U4-dZmIax-P_g&s">

#### 3. Double Click <Name>.jar
<img width="300" alt="install1" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR2UOW09a8y-Ue_FtTFn01C4U4-dZmIax-P_g&s">



### MacOs Installation

#### 1. Double click Event-Hub.zip
<img width="300" alt="install1" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR2UOW09a8y-Ue_FtTFn01C4U4-dZmIax-P_g&s">

#### 2. Open Event-Hub folder
<img width="300" alt="install1" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR2UOW09a8y-Ue_FtTFn01C4U4-dZmIax-P_g&s">

#### 3. Double click Event-Hub.jar
<img width="300" alt="install1" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR2UOW09a8y-Ue_FtTFn01C4U4-dZmIax-P_g&s">


## ตัวอย่างข้อมูลผู้ใช้ระบบ

| username    |   password    | role               |
|-------------|:-------------:|--------------------|
| admin       | admin12345678 | admin              |
| fscikes     |    kzf695     | faculty officer    |
| fscicho     |    gfs475     | department officer |
| fscinuc     |    sqc805     | advisor            |
| b6610458991 |    zxe508     | student            |

## การวางโครงสร้างไฟล์ของโครงงาน
<pre>
───cs211-project-get-grade-a
    ├───data
    │   ├───profile-pictures
    │   ├───request-forms
    │   ├───request-forms-pdf
    │   └───users
    └───src
        └───main
            ├───java
            │   └───ku
            │       └───cs
            │           ├───config
            │           ├───controllers
            │           │   ├───admin (เก็บ Controller ของ Admin)
            │           │   ├───advisor (เก็บ Controller ของ Advisor)
            │           │   ├───components (เก็บ Controller ที่มีการเรียกใช้งานร่วมกัน)
            │           │   │   ├───navigationbars (เก็บ Controller ที่ควบคุม Navigationbar)
            │           │   │   └───tables (เก็บ Controller ที่ควบคุม Table)
            │           │   ├───officer
            │           │   │   ├───department (เก็บ Controller ของ Department)
            │           │   │   └───faculty (เก็บ Controller ของ Faculty)
            │           │   ├───requestforms (เก็บ Controller ของ Requestform)
            │           │   └───student (เก็บ Controller ของ Student)
            │           ├───cs211671project
            │           ├───models (เก็บโมเดล)
            │           │   ├───collections (เก็บ collection)
            │           │   ├───requestforms (เก็บ Model ของ Requestform ทุกประเภท)
            │           │   └───users (เก็บ Model ของ User ทุกบทบาท)
            │           │      
            │           └───services
            │               ├───datahandle
            │               ├───datasource
            │               └───popup
            └───resources
                ├───images (เก็บรูปภาพที่ใช้ในโปรแกรม)
                │   ├───developer-pictures
                │   └───navbar-icons (เก็บภาพของ Icon ที่ใช้ใน Navigationbar)
                └───ku
                    └───cs
                        └───views
                            ├───admin (เก็บไฟล์ fxml ของ Admin)
                            ├───advisor (เก็บไฟล์ fxml ของ Advisor)
                            ├───components (เก็บไฟล์ fxml ของ component ที่มีการเรียกใช้งานร่วมกัน)
                            ├───officer (เก็บไฟล์ fxml ของ Officer)
                            │   ├───department
                            │   └───faculty
                            ├───request-forms (เก็บไฟล์ fxml ของ Requestform)
                            ├───student (เก็บไฟล์ fxml ของ Student)
                            └───styles (เก็บไฟล์ CSS หรือสไตล์ที่ใช้ในโปรแกรม)
</pre>

