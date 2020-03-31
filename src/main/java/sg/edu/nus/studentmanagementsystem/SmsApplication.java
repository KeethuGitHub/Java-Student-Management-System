package sg.edu.nus.studentmanagementsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;

import sg.edu.nus.studentmanagementsystem.models.entitymodels.Admin;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Course;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Department;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Enrollment;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Faculty;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Semester;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Student;
import sg.edu.nus.studentmanagementsystem.models.entitymodels.Module;
import sg.edu.nus.studentmanagementsystem.repositories.AdminRepository;
import sg.edu.nus.studentmanagementsystem.repositories.CourseRepository;
import sg.edu.nus.studentmanagementsystem.repositories.DepartmentRepository;
import sg.edu.nus.studentmanagementsystem.repositories.EnrollmentRepository;
import sg.edu.nus.studentmanagementsystem.repositories.FacultyRepository;
import sg.edu.nus.studentmanagementsystem.repositories.ModuleRepository;
import sg.edu.nus.studentmanagementsystem.repositories.SemesterRepository;
import sg.edu.nus.studentmanagementsystem.repositories.StudentRepository;

@SpringBootApplication
public class SmsApplication {

	private static final Logger log = LoggerFactory.getLogger(SmsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SmsApplication.class, args);
	}

	@Bean
	public CommandLineRunner dbInit(StudentRepository studentrepo, SemesterRepository semesterrepo, AdminRepository adminrepo,
			DepartmentRepository departmentrepo, FacultyRepository facultyrepo, CourseRepository courserepo, EnrollmentRepository enrollmentrepo,
			ModuleRepository modulerepo) 
	{
		return (args) -> {
			log.info("***** DATABASE INITIALIZATION START *****");
			Semester sem1 = new Semester();
			sem1.setId(151601);
			sem1.setName("AY1516 Semester 1");
			Semester sem2 = new Semester();
			sem2.setId(151602);
			sem2.setName("AY1516 Semester 2");
			Semester sem3 = new Semester();
			sem3.setId(161701);
			sem3.setName("AY1617 Semester 1");
			Semester sem4 = new Semester();
			sem4.setId(161702);
			sem4.setName("AY1617 Semester 2");
			Semester sem5 = new Semester();
			sem5.setId(171801);
			sem5.setName("AY1718 Semester 1");
			Semester sem6 = new Semester();
			sem6.setId(171802);
			sem6.setName("AY1718 Semester 2");
			Semester sem7 = new Semester();
			sem7.setId(181901);
			sem7.setName("AY1819 Semester 1");
			Semester sem8 = new Semester();
			sem8.setId(181902);
			sem8.setName("AY1819 Semester 2");
			Semester sem9 = new Semester();
			sem9.setId(192001);
			sem9.setName("AY1920 Semester 1");
			Semester sem10 = new Semester();
			sem10.setId(192002);
			sem10.setName("AY1920 Semester 2");
			
			Student s1 = new Student();
			s1.setUsername("S00001");
			s1.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
			s1.setFirstName("Janice");
			s1.setLastName("Tan");
			s1.setAddress("Blk 123 S87349");
			s1.setContact("98374832");
			s1.setDateOfBirth("1991-09-12");
			s1.setEmail("issntt@nus.edu.sg");
			s1.setGender('F');
			s1.setMajor("Degree of Computer Science");
			s1.setCgpa(5.0);
			
			Student s2 = new Student();
			s2.setUsername("S00002");
			s2.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
			s2.setFirstName("Jason");
			s2.setLastName("Wong");
			s2.setAddress("Blk 67 Kent Ridge Avenue S893284");
			s2.setContact("97834893");
			s2.setDateOfBirth("1991-09-14");
			s2.setEmail("issntt@nus.edu.sg");
			//s2.setEmail("e0457790@u.nus.edu");
			s2.setGender('M');
			s2.setMajor("Degree of Computer Science");
			s2.setCgpa(5.0);
			
			Student s3 = new Student();
			s3.setUsername("S00003");
			s3.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
			s3.setFirstName("Eliane");
			s3.setLastName("Will");
			s3.setAddress("Blk 149C Jalan Schmidt Avenue S978152");
			s3.setContact("65570608");
			s3.setDateOfBirth("1990-11-09");
			s3.setEmail("yang_hu@alumni.sutd.edu.sg");
			s3.setGender('F');
			s3.setMajor("Degree of Computer Science");
			s3.setCgpa(5.0);
			
			Student s4 = new Student();
			s4.setUsername("S00004");
			s4.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
			s4.setFirstName("Dalton");
			s4.setLastName("Cassin");
			s4.setAddress("078 Braun Lane Crescent S052563");
			s4.setContact("77857291");
			s4.setDateOfBirth("1993-09-14");
			s4.setEmail("issspa@nus.edu.sg");
			s4.setGender('M');
			s4.setMajor("Degree of Computer Science");
			s4.setCgpa(5.0);
			
			Student s5 = new Student();
			s5.setUsername("S00005");
			s5.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
			s5.setFirstName("Onie");
			s5.setLastName("Mante");
			s5.setAddress("493 Parisian Way Grove S567736");
			s5.setContact("71600885");
			s5.setDateOfBirth("1994-07-04");
			//s5.setEmail("hlhyly950117@gmail.com");
			s5.setEmail("issspa@nus.edu.sg");
			s5.setGender('F');
			s5.setMajor("Degree of Computer Science");
			s5.setCgpa(5.0);
			
//			Student s6 = new Student();
//			s6.setUsername("S00006");
//			s6.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
//			s6.setFirstName("Ardith");
//			s6.setLastName("Gleichner");
//			s6.setAddress("Blk 079B McClure Bridge Link S380788");
//			s6.setContact("67648677");
//			s6.setDateOfBirth("1992-08-12");
//			s6.setEmail("Jleeyongsiang@gmail.com");
//			s6.setGender('M');
//			s6.setMajor("Degree of Computer Science");
			
//			Student s7 = new Student();
//			s7.setUsername("S00007");
//			s7.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
//			s7.setFirstName("Heidi");
//			s7.setLastName("O'Connell");
//			s7.setAddress("Blk 768H Rodriguez Park Bridge S530254");
//			s7.setContact("94230936");
//			s7.setDateOfBirth("1995-06-26");
//			s7.setEmail("hirthe.kiana@greenfelder.com");
//			s7.setGender('M');
//			s7.setMajor("Degree of Computer Science");
//			
//			Student s8 = new Student();
//			s8.setUsername("S00008");
//			s8.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
//			s8.setFirstName("Winston");
//			s8.setLastName("Borer");
//			s8.setAddress("Blk 074G Schmidt Road Link S575881");
//			s8.setContact("64479540");
//			s8.setDateOfBirth("1991-07-18");
//			s8.setEmail("rath.velma@bruen.net");
//			s8.setGender('M');
//			s8.setMajor("Degree of Computer Science");
//			
//			Student s9 = new Student();
//			s9.setUsername("S00009");
//			s9.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
//			s9.setFirstName("Nannie");
//			s9.setLastName("Schneider");
//			s9.setAddress("731 Lueilwitz Hill Way S172124");
//			s9.setContact("91968700");
//			s9.setDateOfBirth("1996-10-30");
//			s9.setEmail("demario.gottlieb@gmail.com");
//			s9.setGender('F');
//			s9.setMajor("Degree of Computer Science");
//			
//			Student s10 = new Student();
//			s10.setUsername("S00010");
//			s10.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
//			s10.setFirstName("Modesto");
//			s10.setLastName("Okuneva Sr.");
//			s10.setAddress("121 Gislason Way Grove S151164");
//			s10.setContact("62974705");
//			s10.setDateOfBirth("1994-05-26");
//			s10.setEmail("kkirlin@gmail.com");
//			s10.setGender('M');
//			s10.setMajor("Degree of Computer Science");

			sem6.addStudent(s1);
			sem7.addStudent(s1);
			sem8.addStudent(s1);
			sem9.addStudent(s1);
			sem10.addStudent(s1);
			
			sem6.addStudent(s2);
			sem7.addStudent(s2);
			sem8.addStudent(s2);
			sem9.addStudent(s2);
			sem10.addStudent(s2);
			
			sem6.addStudent(s3);
			sem7.addStudent(s3);
			sem8.addStudent(s3);
			sem9.addStudent(s3);
			sem10.addStudent(s3);
			
			sem6.addStudent(s4);
			sem7.addStudent(s4);
			sem8.addStudent(s4);
			sem9.addStudent(s4);
			sem10.addStudent(s4);
			
			sem6.addStudent(s5);
			sem7.addStudent(s5);
			sem8.addStudent(s5);
			sem9.addStudent(s5);
			sem10.addStudent(s5);
			
//			sem6.addStudent(s6);
//			sem7.addStudent(s6);
//			sem8.addStudent(s6);
//			sem9.addStudent(s6);
//			sem10.addStudent(s6);
			
//			sem5.addStudent(s6);
//			sem6.addStudent(s6);
//			sem7.addStudent(s6);
//			sem8.addStudent(s6);
//			sem9.addStudent(s6);
//			sem10.addStudent(s6);
//			sem9.addStudent(s7);
//			sem10.addStudent(s7);
//			sem7.addStudent(s8);
//			sem8.addStudent(s8);
//			sem9.addStudent(s8);
//			sem10.addStudent(s8);
//			sem3.addStudent(s9);
//			sem4.addStudent(s9);
//			sem5.addStudent(s9);
//			sem6.addStudent(s9);
//			sem7.addStudent(s9);
//			sem8.addStudent(s9);
//			sem9.addStudent(s9);
//			sem10.addStudent(s9);
//			sem9.addStudent(s10);
//			sem10.addStudent(s10);
//			
			studentrepo.save(s1);
			studentrepo.save(s2);
			studentrepo.save(s3);
			studentrepo.save(s4);
			studentrepo.save(s5);
//			studentrepo.save(s6);
//			studentrepo.save(s7);
//			studentrepo.save(s8);
//			studentrepo.save(s9);
//			studentrepo.save(s10);
			semesterrepo.save(sem1);
			semesterrepo.save(sem2);
			semesterrepo.save(sem3);
			semesterrepo.save(sem4);
			semesterrepo.save(sem5);
			semesterrepo.save(sem6);
			semesterrepo.save(sem7);
			semesterrepo.save(sem8);
			semesterrepo.save(sem9);
			semesterrepo.save(sem10);
			
			Admin a1 = new Admin();
			a1.setUsername("A00001");
			a1.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
			a1.setFirstName("Wong");
			a1.setLastName("Gensen");
			a1.setAddress("Blk 45 Woodlands Avenue 3 S87349");
			a1.setContact("98513232");
			a1.setDateOfBirth("1991-08-11");
			a1.setEmail("GensenWong@gmail.com");
			a1.setGender('M');

			Admin a2 = new Admin();
			a2.setUsername("A00002");
			a2.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
			a2.setFirstName("See");
			a2.setLastName("Di Yao");
			a2.setAddress("Blk 1 Sembawang Avenue 4 S342349");
			a2.setContact("97364762");
			a2.setDateOfBirth("1991-03-21");
			a2.setEmail("SeeDiYao@gmail.com");
			a2.setGender('M');
			
			Admin a3 = new Admin();
			a3.setUsername("A00003");
			a3.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
			a3.setFirstName("Laron");
			a3.setLastName("Upton");
			a3.setAddress("Blk 778G Waters Alley Highway S655490");
			a3.setContact("82352268");
			a3.setDateOfBirth("1995-06-07");
			a3.setEmail("natalie47@hamill.biz");
			a3.setGender('F');
			
			Admin a4 = new Admin();
			a4.setUsername("A00004");
			a4.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
			a4.setFirstName("Theresa");
			a4.setLastName("Waelchi");
			a4.setAddress("74 Jalan Hudson Highway S767997");
			a4.setContact("67363607");
			a4.setDateOfBirth("1991-03-01");
			a4.setEmail("lebsack.camila@gmail.com");
			a4.setGender('M');

			adminrepo.save(a1);
			adminrepo.save(a2);
			adminrepo.save(a3);
			adminrepo.save(a4);			

			Department d1 = new Department();
			d1.setName("School of Computering");
			Department d2 = new Department();
			d2.setName("School of Engineering");
			Department d3 = new Department();
			d3.setName("School of Arts and Social Science");
						
			Faculty f1 = new Faculty();
			f1.setUsername("F00001");
			f1.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
			f1.setFirstName("Janice");
			f1.setLastName("Wong");
			f1.setAddress("Blk 13 Kranji Street 4 S123435");
			f1.setContact("97364762");
			f1.setDateOfBirth("1970-11-01");
			f1.setEmail("JaniceWong@gmail.com");
			f1.setGender('F');
			
			Faculty f2 = new Faculty();
			f2.setUsername("F00002");
			f2.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
			f2.setFirstName("Yuen Kuen");
			f2.setLastName("Tan");
			f2.setAddress("Blk 4 Yew Tee Avenue 11 S908435");
			f2.setContact("97234234");
			f2.setDateOfBirth("1975-08-12");
			f2.setEmail("YuenKuenTan@gmail.com");
			f2.setGender('M');
			
			Faculty f3 = new Faculty();
			f3.setUsername("F00003");
			f3.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
			f3.setFirstName("Tin");
			f3.setLastName("Ngoc");
			f3.setAddress("Blk 35 Kranji Street 11 S895354");
			f3.setContact("97844565");
			f3.setDateOfBirth("1980-07-21");
			f3.setEmail("TinNgoc@gmail.com");
			f3.setGender('M');
			
			Faculty f4 = new Faculty();
			f4.setUsername("F00004");
			f4.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
			f4.setFirstName("Yang Ming");
			f4.setLastName("Wang");
			f4.setAddress("Blk 9 Marsiling Street 21 S656457");
			f4.setContact("98854756");
			f4.setDateOfBirth("1965-07-21");
			f4.setEmail("YangMingWang@gmail.com");
			f4.setGender('M');
			
			Faculty f5 = new Faculty();
			f5.setUsername("F00005");
			f5.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
			f5.setFirstName("John");
			f5.setLastName("Richard");
			f5.setAddress("Blk 21 Bukit Panjang Avenue 2 S898565");
			f5.setContact("96435126");
			f5.setDateOfBirth("1973-02-18");
			f5.setEmail("JohnRichard@gmail.com");
			f5.setGender('M');
			
			Faculty f6 = new Faculty();
			f6.setUsername("F00006");
			f6.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
			f6.setFirstName("Sarah");
			f6.setLastName("Prichett");
			f6.setAddress("Blk 2 Orchard Tower S635435");
			f6.setContact("99863234");
			f6.setDateOfBirth("1978-05-18");
			f6.setEmail("SarahPrichett@gmail.com");
			f6.setGender('F');
			
			Faculty f7 = new Faculty();
			f7.setUsername("F00007");
			f7.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
			f7.setFirstName("Orville");
			f7.setLastName("Schimmel II");
			f7.setAddress("76 Jalan Olson Crescent S506723");
			f7.setContact("69919427");
			f7.setDateOfBirth("1961-06-22");
			f7.setEmail("nolan.weber@mclaughlin.com");
			f7.setGender('M');
			
			Faculty f8 = new Faculty();
			f8.setUsername("F00008");
			f8.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
			f8.setFirstName("Nelson");
			f8.setLastName("McLaughlin");
			f8.setAddress("Blk 262A Homenick Quay Lane S474384");
			f8.setContact("65422883");
			f8.setDateOfBirth("1972-01-02");
			f8.setEmail("judah.ritchie@hotmail.com");
			f8.setGender('M');
			
			Faculty f9 = new Faculty();
			f9.setUsername("F00009");
			f9.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
			f9.setFirstName("Hugh");
			f9.setLastName("VonRueden");
			f9.setAddress("240 Jalan Farrell Way S046295");
			f9.setContact("83719504");
			f9.setDateOfBirth("1985-01-02");
			f9.setEmail("mitchell85@bartell.com");
			f9.setGender('M');
			
			Faculty f10 = new Faculty();
			f10.setUsername("F00010");
			f10.setPassword(BCrypt.hashpw("pw", BCrypt.gensalt(10)));
			f10.setFirstName("Cicero");
			f10.setLastName("Murazik");
			f10.setAddress("Blk 135F Wolff Park Grove S421227");
			f10.setContact("63875212");
			f10.setDateOfBirth("1953-12-06");
			f10.setEmail("mario.treutel@hotmail.com");
			f10.setGender('M');
			
			departmentrepo.save(d1);
			departmentrepo.save(d2);
			departmentrepo.save(d3);
			
			d1.addFaculty(f1);
			d1.addFaculty(f2);
			d1.addFaculty(f3);
			d2.addFaculty(f4);
			d2.addFaculty(f5);
			d2.addFaculty(f6);
			d2.addFaculty(f7);
			d3.addFaculty(f8);
			d3.addFaculty(f9);
			d3.addFaculty(f10);
			
			facultyrepo.save(f1);
			facultyrepo.save(f2);
			facultyrepo.save(f3);
			facultyrepo.save(f4);
			facultyrepo.save(f5);
			facultyrepo.save(f6);
			facultyrepo.save(f7);
			facultyrepo.save(f8);
			facultyrepo.save(f9);
			facultyrepo.save(f10);
			
			Module m1 = new Module(); 
			m1.setModulename("Introduction to Information Theory");
			m1.setModulecode("CS1001");
			m1.setCredit(4);
			
			Module m2 = new Module(); 
			m2.setModulename("Introduction to Internet of Things");
			m2.setModulecode("CS1002");
			m2.setCredit(4);
			
		
			Module m3 = new Module(); 
			m3.setModulename("Mechanical Engineering and Society");
			m3.setModulecode("EG1001");
			m3.setCredit(4);
			
			Module m4 = new Module(); 
			m4.setModulename("Mechanics of Material");
			m4.setModulecode("EG1002");
			m4.setCredit(4);

			Module m5 = new Module(); 
			m5.setModulename("Values & Skills for Helping Relationships");
			m5.setModulecode("AS1001");
			m5.setCredit(4);
			

			Module m6 = new Module(); 
			m6.setModulename("Social Work in Medical Settings");
			m6.setModulecode("AS1002");
			m6.setCredit(4);
			
			Module m7 = new Module(); 
			m7.setModulename("Organic Chemistry");
			m7.setModulecode("EG1003");
			m7.setCredit(4);
			
			Module m8 = new Module(); 
			m8.setModulename("Social Psychology");
			m8.setModulecode("AS1003");
			m8.setCredit(4);
			
			Module m9 = new Module(); 
			m9.setModulename("Anarchist Economics");
			m9.setModulecode("AS1004");
			m9.setCredit(4);
			
			Module m10 = new Module(); 
			m10.setModulename("Moral Psychology And Descriptive Ethics");
			m10.setModulecode("AS1005");
			m10.setCredit(4);
			
			Module m11 = new Module(); 
			m11.setModulename("Sociology Of Religion");
			m11.setModulecode("AS1006");
			m11.setCredit(4);
			
			Module m12 = new Module(); 
			m12.setModulename("Poetry");
			m12.setModulecode("AS2001");
			m12.setCredit(4);
			
			Module m13 = new Module(); 
			m13.setModulename("Performing Arts");
			m13.setModulecode("AS2002");
			m13.setCredit(4);
			
			Module m14 = new Module(); 
			m14.setModulename("Memetic Visualisation");
			m14.setModulecode("CS1003");
			m14.setCredit(4);
			
			Module m15 = new Module(); 
			m15.setModulename("Planetary Science");
			m15.setModulecode("EG1004");
			m15.setCredit(4);
//			
//			Module m16 = new Module(); 
//			m16.setModulename("Physical Geography : Landscape Ecology");
//			m16.setModulecode("EG1005");
//			m16.setCredit(4);
			

//			
//			Course c17 = new Course();
//			c17.setName("Life Cycle Visualisation");
//			c17.setCoursecode("CS100410");
//			c17.setCredit(4);
//			c17.setSize(50);
//			
//			Course c18 = new Course();
//			c18.setName("Automotive Robotics");
//			c18.setCoursecode("CS100505");
//			c18.setCredit(4);
//			c18.setSize(50);
//			
//			Course c19 = new Course();
//			c19.setName("Aerospace Engineering : Astronautics");
//			c19.setCoursecode("EG100605");
//			c19.setCredit(4);
//			c19.setSize(50);
//			
//			Course c20 = new Course();
//			c20.setName("Summary of programming languages");
//			c20.setCoursecode("CS100603");
//			c20.setCredit(4);
//			c20.setSize(50);
//			
//			Course c21 = new Course();
//			c21.setName("Probability Theory : Stochastic Process");
//			c21.setCoursecode("CS100710");
//			c21.setCredit(4);
//			c21.setSize(50);
//			
//			Course c22 = new Course();
//			c22.setName("Comparative Literature");
//			c22.setCoursecode("AS100906");
//			c22.setCredit(4);
//			c22.setSize(50);
//			
//			Course c23 = new Course();
//			c23.setName("Social Psychology");
//			c23.setCoursecode("AS101006");
//			c23.setCredit(4);
//			c23.setSize(50);
//			
//			Course c24 = new Course();
//			c24.setName("Advanced Prototyping & Communication");
//			c24.setCoursecode("EG100701");
//			c24.setCredit(4);
//			c24.setSize(50);
//			
//			Course c25 = new Course();
//			c25.setName("Strategic Statics & Technology");
//			c25.setCoursecode("CS100801");
//			c25.setCredit(4);
//			c25.setSize(50);
//			
//			Course c26 = new Course();
//			c26.setName("Industrial Engineering");
//			c26.setCoursecode("EG100802");
//			c26.setCredit(4);
//			c26.setSize(50);
//			
//			Course c27 = new Course();
//			c27.setName("Cartography");
//			c27.setCoursecode("EG100904");
//			c27.setCredit(4);
//			c27.setSize(50);
//			
//			Course c28 = new Course();
//			c28.setName("Industrial process");
//			c28.setCoursecode("EG101007");
//			c28.setCredit(4);
//			c28.setSize(50);
//			
//			Course c29 = new Course();
//			c29.setName("Concept Electronics & Design");
//			c29.setCoursecode("CS100909");
//			c29.setCredit(4);
//			c29.setSize(50);
//			
//			Course c30 = new Course();
//			c30.setName("Visual Dynamics");
//			c30.setCoursecode("CS101003");
//			c30.setCredit(4);
//			c30.setSize(50);
//			
			Course c1 = new Course();
			c1.setSize(50);
			Course c2 = new Course();
			c2.setSize(50);
			Course c3 = new Course();
			c3.setSize(50);
			Course c4 = new Course();
			c4.setSize(50);
			Course c5 = new Course();
			c5.setSize(50);
			Course c6 = new Course();
			c6.setSize(50);
			Course c7 = new Course();
			c7.setSize(50);
			Course c8 = new Course();
			c8.setSize(50);
			Course c9 = new Course();
			c9.setSize(50);
			Course c10 = new Course();
			c10.setSize(50);
			Course c11 = new Course();
			c11.setSize(50);
			Course c12 = new Course();
			c12.setSize(50);
			Course c13 = new Course();
			c13.setSize(50);
			Course c14 = new Course();
			c14.setSize(50);
			Course c15 = new Course();
			c15.setSize(50);
			Course c16 = new Course();
			c16.setSize(50);
			Course c17 = new Course();
			c17.setSize(50);
			Course c18 = new Course();
			c18.setSize(50);
			Course c19 = new Course();
			c19.setSize(50);
			Course c20 = new Course();
			c20.setSize(50);
			Course c21 = new Course();
			c21.setSize(50);
			Course c22 = new Course();
			c22.setSize(50);
			Course c23 = new Course();
			c23.setSize(50);
			Course c24 = new Course();
			c24.setSize(50);
			Course c25 = new Course();
			c25.setSize(50);
			Course c26 = new Course();
			c26.setSize(50);
			Course c27 = new Course();
			c27.setSize(50);
//			Course c28 = new Course();
//			c28.setSize(50);
//			Course c29 = new Course();
//			c29.setSize(50);
//			Course c30 = new Course();
//			c30.setSize(50);
//			Course c31 = new Course();
//			c31.setSize(50);
//			Course c32 = new Course();
//			c32.setSize(50);
//			Course c33 = new Course();
//			c33.setSize(50);
//			Course c34 = new Course();
//			c34.setSize(50);
//			Course c35 = new Course();
//			c35.setSize(50);
//			Course c36 = new Course();
//			c36.setSize(50);
//			Course c37 = new Course();
//			c37.setSize(50);
//			Course c38 = new Course();
//			c38.setSize(50);
//			Course c39 = new Course();
//			c39.setSize(50);
//			Course c40 = new Course();
//			c40.setSize(50);
//			Course c41 = new Course();
//			c41.setSize(50);
//			Course c42 = new Course();
//			c42.setSize(50);
//			Course c43 = new Course();
//			c43.setSize(50);
//			Course c44 = new Course();
//			c44.setSize(50);
//			Course c45 = new Course();
//			c45.setSize(50);
			
			
			modulerepo.save(m1);
			modulerepo.save(m2);
			modulerepo.save(m3);
			modulerepo.save(m4);
			modulerepo.save(m5);
			modulerepo.save(m6);
			modulerepo.save(m7);
			modulerepo.save(m8);
			modulerepo.save(m9);
			modulerepo.save(m10);
			modulerepo.save(m11);
			modulerepo.save(m12);
			modulerepo.save(m13);
			modulerepo.save(m14);
			modulerepo.save(m15);
			
			m1.addCourse(c1);
			m2.addCourse(c2);
			m3.addCourse(c3);
			m4.addCourse(c4);
			m5.addCourse(c5);
			m6.addCourse(c6);
			m7.addCourse(c7);
			m8.addCourse(c8);
			m9.addCourse(c9);
			m10.addCourse(c10);
			m11.addCourse(c11);
			m12.addCourse(c12);
			m13.addCourse(c13);
			m14.addCourse(c14);
			m15.addCourse(c15);
			
			m1.addCourse(c16);
			m2.addCourse(c17);
			m3.addCourse(c18);
			m4.addCourse(c19);
			m5.addCourse(c20);
			m6.addCourse(c21);
			m7.addCourse(c22);
			m8.addCourse(c23);
			m9.addCourse(c24);
			m10.addCourse(c25);
			m11.addCourse(c26);
			m12.addCourse(c27);
			
			
			
			
			d1.addCourse(c1);
			d1.addCourse(c2);
			d1.addCourse(c3);
			d1.addCourse(c4);
			d1.addCourse(c5);
			d2.addCourse(c6);
			d2.addCourse(c7);
			d2.addCourse(c8);
			d2.addCourse(c9);
			d2.addCourse(c10);
			d3.addCourse(c11);
			d3.addCourse(c12);
			d3.addCourse(c13);
			d3.addCourse(c14);
			d3.addCourse(c15);
			d2.addCourse(c16);
			d2.addCourse(c17);
			d2.addCourse(c18);
			d2.addCourse(c19);
			d2.addCourse(c20);
			d3.addCourse(c21);
			d3.addCourse(c22);
			d3.addCourse(c23);
			d3.addCourse(c24);
			d3.addCourse(c25);
			d3.addCourse(c26);
			d3.addCourse(c27);
//			d3.addCourse(c28);
//			d3.addCourse(c29);
//			d3.addCourse(c30);
			
//			sem1.addCourse(c25);
//			sem1.addCourse(c24);
//			sem1.addCourse(c9);
//			sem2.addCourse(c15);
//			sem2.addCourse(c4);
//			sem2.addCourse(c26);
//			sem3.addCourse(c2);
//			sem3.addCourse(c20);
//			sem3.addCourse(c10);
//			sem4.addCourse();
//			sem4.addCourse();
//			sem4.addCourse();
//			sem4.addCourse();
//			sem4.addCourse();
//			sem4.addCourse();
			
//			sem5.addCourse();
//			sem5.addCourse();
//			sem5.addCourse();
//			sem5.addCourse();
//			sem5.addCourse();
//			sem5.addCourse();
			
			sem6.addCourse(c1);
			sem6.addCourse(c2);
			sem6.addCourse(c3);
			sem7.addCourse(c4);
			sem7.addCourse(c5);
			sem7.addCourse(c6);
			sem8.addCourse(c7);
			sem8.addCourse(c8);
			sem8.addCourse(c9);
			sem9.addCourse(c10);
			sem9.addCourse(c11);
			sem9.addCourse(c12);
			sem10.addCourse(c13);
			sem10.addCourse(c14);
			sem10.addCourse(c15);
			
			sem10.addCourse(c16);
			sem10.addCourse(c17);
			sem10.addCourse(c18);
			sem10.addCourse(c19);
			sem10.addCourse(c20);
			sem10.addCourse(c21);
			sem10.addCourse(c22);
			sem10.addCourse(c23);
			sem10.addCourse(c24);
			sem10.addCourse(c25);
			sem10.addCourse(c26);
			sem10.addCourse(c27);
			
			f1.addCourse(c1);
			f1.addCourse(c2);
			f1.addCourse(c3);
			f2.addCourse(c4);
			f2.addCourse(c5);
			f2.addCourse(c6);
			f3.addCourse(c7);
			f3.addCourse(c8);
			f3.addCourse(c9);
			f3.addCourse(c10);
			f4.addCourse(c11);
			f4.addCourse(c12);
			f4.addCourse(c13);
			f5.addCourse(c14);
			f5.addCourse(c15);
			f5.addCourse(c16);
			f6.addCourse(c17);
			f6.addCourse(c18);
			f6.addCourse(c19);
			f7.addCourse(c20);
			f8.addCourse(c21);
			f8.addCourse(c22);
			f8.addCourse(c23);
			f9.addCourse(c24);
			f9.addCourse(c25);
			f9.addCourse(c26);
			f10.addCourse(c27);
//			f10.addCourse(c28);
//			f10.addCourse(c29);
//			f10.addCourse(c30);
			
			courserepo.save(c1);
			courserepo.save(c2);
			courserepo.save(c3);
			courserepo.save(c4);
			courserepo.save(c5);
			courserepo.save(c6);
			courserepo.save(c7);
			courserepo.save(c8);
			courserepo.save(c9);
			courserepo.save(c10);
			courserepo.save(c11);
			courserepo.save(c12);
			courserepo.save(c13);
			courserepo.save(c14);
			courserepo.save(c15);
			
			courserepo.save(c16);
			courserepo.save(c17);
			courserepo.save(c18);
			courserepo.save(c19);
			courserepo.save(c20);
			courserepo.save(c21);
			courserepo.save(c22);
			courserepo.save(c23);
			courserepo.save(c24);
			courserepo.save(c25);
			courserepo.save(c26);
			courserepo.save(c27);
//			courserepo.save(c16);
//			courserepo.save(c17);
//			courserepo.save(c18);
//			courserepo.save(c19);
//			courserepo.save(c20);
//			courserepo.save(c21);
//			courserepo.save(c22);
//			courserepo.save(c23);
//			courserepo.save(c24);
//			courserepo.save(c25);
//			courserepo.save(c26);
//			courserepo.save(c27);
//			courserepo.save(c28);
//			courserepo.save(c29);
//			courserepo.save(c30);
			
			
			Enrollment e1 = new Enrollment();
			e1.setEnrollmentStatus("Completed");
			e1.setGrade("A+");
			
			Enrollment e2 = new Enrollment();
			e2.setEnrollmentStatus("Completed");
			e2.setGrade("A+");
			
			Enrollment e3 = new Enrollment();
			e3.setEnrollmentStatus("Completed");
			e3.setGrade("A+");
			
			Enrollment e4 = new Enrollment();
			e4.setEnrollmentStatus("Completed");
			e4.setGrade("A+");
			
			Enrollment e5 = new Enrollment();
			e5.setEnrollmentStatus("Completed");
			e5.setGrade("A+");
			
			Enrollment e6 = new Enrollment();
			e6.setEnrollmentStatus("Completed");
			e6.setGrade("A+");
			
			Enrollment e7 = new Enrollment();
			e7.setEnrollmentStatus("Completed");
			e7.setGrade("A+");
			
			Enrollment e8 = new Enrollment();
			e8.setEnrollmentStatus("Completed");
			e8.setGrade("A+");
			
			Enrollment e9 = new Enrollment();
			e9.setEnrollmentStatus("Completed");
			e9.setGrade("A+");
			
			Enrollment e10 = new Enrollment();
			e10.setEnrollmentStatus("Completed");
			e10.setGrade("A+");
			
			Enrollment e11 = new Enrollment();
			e11.setEnrollmentStatus("Completed");
			e11.setGrade("A+");
			
			Enrollment e12 = new Enrollment();
			e12.setEnrollmentStatus("Enrolled");
			
			Enrollment e13 = new Enrollment();
			e13.setEnrollmentStatus("Pending Approval");
			
			Enrollment e14 = new Enrollment();
			e14.setEnrollmentStatus("Pending Approval");
			
			Enrollment e15 = new Enrollment();
			e15.setEnrollmentStatus("Pending Approval");
			
			Enrollment e16 = new Enrollment();
			e16.setEnrollmentStatus("Completed");
			e16.setGrade("A+");
			
			Enrollment e17 = new Enrollment();
			e17.setEnrollmentStatus("Completed");
			e17.setGrade("A+");
			
			Enrollment e18 = new Enrollment();
			e18.setEnrollmentStatus("Rejected");
			
			Enrollment e19 = new Enrollment();
			e19.setEnrollmentStatus("Completed");
			e19.setGrade("A+");
			
			Enrollment e20 = new Enrollment();
			e20.setEnrollmentStatus("Completed");
			e20.setGrade("A+");
			
			Enrollment e21 = new Enrollment();
			e21.setEnrollmentStatus("Rejected");
			
			Enrollment e22 = new Enrollment();
			e22.setEnrollmentStatus("Completed");
			e22.setGrade("A+");
			
			Enrollment e23 = new Enrollment();
			e23.setEnrollmentStatus("Completed");
			e23.setGrade("A+");
			
			Enrollment e24 = new Enrollment();
			e24.setEnrollmentStatus("Completed");
			e24.setGrade("A+");
			
			Enrollment e25 = new Enrollment();
			e25.setEnrollmentStatus("Completed");
			e25.setGrade("A+");
			
			Enrollment e26 = new Enrollment();
			e26.setEnrollmentStatus("Enrolled");
			
			Enrollment e27 = new Enrollment();
			e27.setEnrollmentStatus("Enrolled");
			
			Enrollment e28 = new Enrollment();
			e28.setEnrollmentStatus("Pending Approval");
			
			Enrollment e29 = new Enrollment();
			e29.setEnrollmentStatus("Pending Approval");
			
			Enrollment e30 = new Enrollment();
			e30.setEnrollmentStatus("Pending Approval");

			Enrollment e31 = new Enrollment();
			e31.setEnrollmentStatus("Completed");
			e31.setGrade("A+");
			
			Enrollment e32 = new Enrollment();
			e32.setEnrollmentStatus("Completed");
			e32.setGrade("A+");
			
			Enrollment e33 = new Enrollment();
			e33.setEnrollmentStatus("Completed");
			e33.setGrade("A+");
			
			Enrollment e34 = new Enrollment();
			e34.setEnrollmentStatus("Completed");
			e34.setGrade("A+");
			
			Enrollment e35 = new Enrollment();
			e35.setEnrollmentStatus("Completed");
			e35.setGrade("A+");
			
			Enrollment e36 = new Enrollment();
			e36.setEnrollmentStatus("Completed");
			e35.setGrade("A+");
			
			Enrollment e37 = new Enrollment();
			e37.setEnrollmentStatus("Completed");
			e37.setGrade("A+");
			
			Enrollment e38 = new Enrollment();
			e38.setEnrollmentStatus("Completed");
			e38.setGrade("A+");
			
			Enrollment e39 = new Enrollment();
			e39.setEnrollmentStatus("Rejected");
			
			Enrollment e40 = new Enrollment();
			e40.setEnrollmentStatus("Completed");
			e40.setGrade("A+");
			
			Enrollment e41 = new Enrollment();
			e41.setEnrollmentStatus("Enrolled");
			
			Enrollment e42 = new Enrollment();
			e42.setEnrollmentStatus("Enrolled");
			
			Enrollment e43 = new Enrollment();
			e43.setEnrollmentStatus("Pending Approval");
			
			Enrollment e44 = new Enrollment();
			e44.setEnrollmentStatus("Pending Approval");
			
			Enrollment e45 = new Enrollment();
			e45.setEnrollmentStatus("Pending Approval");
			
			Enrollment e46 = new Enrollment();
			e46.setEnrollmentStatus("Completed");
			e46.setGrade("A+");
			
			Enrollment e47 = new Enrollment();
			e47.setEnrollmentStatus("Completed");
			e47.setGrade("A+");
			
			Enrollment e48 = new Enrollment();
			e48.setEnrollmentStatus("Completed");
			e48.setGrade("A+");
			
			Enrollment e49 = new Enrollment();
			e49.setEnrollmentStatus("Completed");
			e49.setGrade("A+");
			
			Enrollment e50 = new Enrollment();
			e50.setEnrollmentStatus("Completed");
			e50.setGrade("A+");
			
			Enrollment e51 = new Enrollment();
			e51.setEnrollmentStatus("Completed");
			e51.setGrade("A+");
			
			Enrollment e52 = new Enrollment();
			e52.setEnrollmentStatus("Completed");
			e52.setGrade("A+");
			
			Enrollment e53 = new Enrollment();
			e53.setEnrollmentStatus("Completed");
			e53.setGrade("A+");
			
			Enrollment e54 = new Enrollment();
			e54.setEnrollmentStatus("Rejected");
			
			Enrollment e55 = new Enrollment();
			e55.setEnrollmentStatus("Completed");
			e55.setGrade("A+");
			
			Enrollment e56 = new Enrollment();
			e56.setEnrollmentStatus("Completed");
			e56.setGrade("A+");
			
			Enrollment e57 = new Enrollment();
			e57.setEnrollmentStatus("Completed");
			e57.setGrade("A+");
			
			Enrollment e58 = new Enrollment();
			e58.setEnrollmentStatus("Pending Approval");
			
			Enrollment e59 = new Enrollment();
			e59.setEnrollmentStatus("Pending Approval");
			
			Enrollment e60 = new Enrollment();
			e60.setEnrollmentStatus("Pending Approval");
			
			Enrollment e61 = new Enrollment();
			e61.setEnrollmentStatus("Completed");
			e61.setGrade("A+");
			
			Enrollment e62 = new Enrollment();
			e62.setEnrollmentStatus("Completed");
			e62.setGrade("A+");
			
			Enrollment e63 = new Enrollment();
			e63.setEnrollmentStatus("Completed");
			e63.setGrade("A+");
			
			Enrollment e64 = new Enrollment();
			e64.setEnrollmentStatus("Completed");
			e64.setGrade("A+");
			
			Enrollment e65 = new Enrollment();
			e65.setEnrollmentStatus("Completed");
			e65.setGrade("A+");
			
			Enrollment e66 = new Enrollment();
			e66.setEnrollmentStatus("Rejected");
			
			Enrollment e67 = new Enrollment();
			e67.setEnrollmentStatus("Completed");
			e67.setGrade("A+");
			
			Enrollment e68 = new Enrollment();
			e68.setEnrollmentStatus("Completed");
			e68.setGrade("A+");
			
			Enrollment e69 = new Enrollment();
			e69.setEnrollmentStatus("Rejected");
			
			Enrollment e70 = new Enrollment();
			e70.setEnrollmentStatus("Completed");
			e70.setGrade("A+");
			
			Enrollment e71 = new Enrollment();
			e71.setEnrollmentStatus("Enrolled");
			
			Enrollment e72 = new Enrollment();
			e72.setEnrollmentStatus("Enrolled");

			Enrollment e73 = new Enrollment();
			e73.setEnrollmentStatus("Pending Approval");
			
			Enrollment e74 = new Enrollment();
			e74.setEnrollmentStatus("Pending Approval");
			
			Enrollment e75 = new Enrollment();
			e75.setEnrollmentStatus("Pending Approval");

			s1.addEnrollment(e1);
			s1.addEnrollment(e2);
			s1.addEnrollment(e3);
			s1.addEnrollment(e4);
			s1.addEnrollment(e5);
			s1.addEnrollment(e6);
			s1.addEnrollment(e7);
			s1.addEnrollment(e8);
			s1.addEnrollment(e9);
			s1.addEnrollment(e10);
			s1.addEnrollment(e11);
			s1.addEnrollment(e12);
			s1.addEnrollment(e13);
			s1.addEnrollment(e14);
			s1.addEnrollment(e15);
			
			
			s2.addEnrollment(e16);
			s2.addEnrollment(e17);
			s2.addEnrollment(e18);
			s2.addEnrollment(e19);
			s2.addEnrollment(e20);
			s2.addEnrollment(e21);
			s2.addEnrollment(e22);
			s2.addEnrollment(e23);
			s2.addEnrollment(e24);
			s2.addEnrollment(e25);
			s2.addEnrollment(e26);
			s2.addEnrollment(e27);
			s2.addEnrollment(e28);
			s2.addEnrollment(e29);
			s2.addEnrollment(e30);
			
			s3.addEnrollment(e31);
			s3.addEnrollment(e32);
			s3.addEnrollment(e33);
			s3.addEnrollment(e34);
			s3.addEnrollment(e35);
			s3.addEnrollment(e36);
			s3.addEnrollment(e37);
			s3.addEnrollment(e38);
			s3.addEnrollment(e39);
			s3.addEnrollment(e40);
			s3.addEnrollment(e41);
			s3.addEnrollment(e42);
			s3.addEnrollment(e43);
			s3.addEnrollment(e44);
			s3.addEnrollment(e45);
			
			s4.addEnrollment(e46);
			s4.addEnrollment(e47);
			s4.addEnrollment(e48);
			s4.addEnrollment(e49);
			s4.addEnrollment(e50);
			s4.addEnrollment(e51);
			s4.addEnrollment(e52);
			s4.addEnrollment(e53);
			s4.addEnrollment(e54);
			s4.addEnrollment(e55);
			s4.addEnrollment(e56);
			s4.addEnrollment(e57);
			s4.addEnrollment(e58);
			s4.addEnrollment(e59);
			s4.addEnrollment(e60);
			
			s5.addEnrollment(e61);
			s5.addEnrollment(e62);
			s5.addEnrollment(e63);
			s5.addEnrollment(e64);
			s5.addEnrollment(e65);
			s5.addEnrollment(e66);
			s5.addEnrollment(e67);
			s5.addEnrollment(e68);
			s5.addEnrollment(e69);
			s5.addEnrollment(e70);
			s5.addEnrollment(e71);
			s5.addEnrollment(e72);
			s5.addEnrollment(e73);
			s5.addEnrollment(e74);
			s5.addEnrollment(e75);
			
			// 6 classes with 15 enrollments, 3 per student //sem6
			c1.addEnrollment(e1);
			c2.addEnrollment(e2);
			c3.addEnrollment(e3);
			c1.addEnrollment(e16);
			c2.addEnrollment(e17);
			c3.addEnrollment(e18);
			c1.addEnrollment(e31);
			c2.addEnrollment(e32);
			c3.addEnrollment(e33);
			c1.addEnrollment(e46);
			c2.addEnrollment(e47);
			c3.addEnrollment(e48);
			c1.addEnrollment(e61);
			c2.addEnrollment(e62);
			c3.addEnrollment(e63);
			
			//sem7
			c4.addEnrollment(e4);
			c5.addEnrollment(e5);
			c6.addEnrollment(e6);
			c4.addEnrollment(e19);
			c5.addEnrollment(e20);
			c6.addEnrollment(e21);
			c4.addEnrollment(e34);
			c5.addEnrollment(e35);
			c6.addEnrollment(e36);
			c4.addEnrollment(e49);
			c5.addEnrollment(e50);
			c6.addEnrollment(e51);
			c4.addEnrollment(e64);
			c5.addEnrollment(e65);
			c6.addEnrollment(e66);
			
			//sem8
			c7.addEnrollment(e7);
			c8.addEnrollment(e8);
			c9.addEnrollment(e9);
			c7.addEnrollment(e22);
			c8.addEnrollment(e23);
			c9.addEnrollment(e24);
			c7.addEnrollment(e37);
			c8.addEnrollment(e38);
			c9.addEnrollment(e39);
			c7.addEnrollment(e52);
			c8.addEnrollment(e53);
			c9.addEnrollment(e54);
			c7.addEnrollment(e67);
			c8.addEnrollment(e68);
			c9.addEnrollment(e69);
			
			//sem9
			c10.addEnrollment(e10);
			c11.addEnrollment(e11);
			c12.addEnrollment(e12);
			c10.addEnrollment(e25);
			c11.addEnrollment(e26);
			c12.addEnrollment(e27);
			c10.addEnrollment(e40);
			c11.addEnrollment(e41);
			c12.addEnrollment(e42);
			c10.addEnrollment(e55);
			c11.addEnrollment(e56);
			c12.addEnrollment(e57);
			c10.addEnrollment(e70);
			c11.addEnrollment(e71);
			c12.addEnrollment(e72);
			
			//sem10
			c13.addEnrollment(e13);
			c14.addEnrollment(e14);
			c15.addEnrollment(e15);
			c13.addEnrollment(e28);
			c14.addEnrollment(e29);
			c15.addEnrollment(e30);
			c13.addEnrollment(e43);
			c14.addEnrollment(e44);
			c15.addEnrollment(e45);
			c13.addEnrollment(e58);
			c14.addEnrollment(e59);
			c15.addEnrollment(e60);
			c13.addEnrollment(e73);
			c14.addEnrollment(e74);
			c15.addEnrollment(e75);
			
			
			//Semester
			sem6.addEnrollment(e1);
			sem6.addEnrollment(e2);
			sem6.addEnrollment(e3);
			sem6.addEnrollment(e16);
			sem6.addEnrollment(e17);
			sem6.addEnrollment(e18);
			sem6.addEnrollment(e31);
			sem6.addEnrollment(e32);
			sem6.addEnrollment(e33);
			sem6.addEnrollment(e46);
			sem6.addEnrollment(e47);
			sem6.addEnrollment(e48);
			sem6.addEnrollment(e61);
			sem6.addEnrollment(e62);
			sem6.addEnrollment(e63);
			
			//sem7
			sem7.addEnrollment(e4);
			sem7.addEnrollment(e5);
			sem7.addEnrollment(e6);
			sem7.addEnrollment(e19);
			sem7.addEnrollment(e20);
			sem7.addEnrollment(e21);
			sem7.addEnrollment(e34);
			sem7.addEnrollment(e35);
			sem7.addEnrollment(e36);
			sem7.addEnrollment(e49);
			sem7.addEnrollment(e50);
			sem7.addEnrollment(e51);
			sem7.addEnrollment(e64);
			sem7.addEnrollment(e65);
			sem7.addEnrollment(e66);
			
			//sem8
			sem8.addEnrollment(e7);
			sem8.addEnrollment(e8);
			sem8.addEnrollment(e9);
			sem8.addEnrollment(e22);
			sem8.addEnrollment(e23);
			sem8.addEnrollment(e24);
			sem8.addEnrollment(e37);
			sem8.addEnrollment(e38);
			sem8.addEnrollment(e39);
			sem8.addEnrollment(e52);
			sem8.addEnrollment(e53);
			sem8.addEnrollment(e54);
			sem8.addEnrollment(e67);
			sem8.addEnrollment(e68);
			sem8.addEnrollment(e69);
			
			//sem9
			sem9.addEnrollment(e10);
			sem9.addEnrollment(e11);
			sem9.addEnrollment(e12);
			sem9.addEnrollment(e25);
			sem9.addEnrollment(e26);
			sem9.addEnrollment(e27);
			sem9.addEnrollment(e40);
			sem9.addEnrollment(e41);
			sem9.addEnrollment(e42);
			sem9.addEnrollment(e55);
			sem9.addEnrollment(e56);
			sem9.addEnrollment(e57);
			sem9.addEnrollment(e70);
			sem9.addEnrollment(e71);
			sem9.addEnrollment(e72);
			
			//sem10
			sem10.addEnrollment(e13);
			sem10.addEnrollment(e14);
			sem10.addEnrollment(e15);
			sem10.addEnrollment(e28);
			sem10.addEnrollment(e29);
			sem10.addEnrollment(e30);
			sem10.addEnrollment(e43);
			sem10.addEnrollment(e44);
			sem10.addEnrollment(e45);
			sem10.addEnrollment(e58);
			sem10.addEnrollment(e59);
			sem10.addEnrollment(e60);
			sem10.addEnrollment(e73);
			sem10.addEnrollment(e74);
			sem10.addEnrollment(e75);
			
			
			enrollmentrepo.save(e1);
			enrollmentrepo.save(e2);
			enrollmentrepo.save(e3);
			enrollmentrepo.save(e4);
			enrollmentrepo.save(e5);
			enrollmentrepo.save(e6);
			enrollmentrepo.save(e7);
			enrollmentrepo.save(e8);
			enrollmentrepo.save(e9);
			enrollmentrepo.save(e10);
			enrollmentrepo.save(e11);
			enrollmentrepo.save(e12);
			enrollmentrepo.save(e13);
			enrollmentrepo.save(e14);
			enrollmentrepo.save(e15);
			enrollmentrepo.save(e16);
			enrollmentrepo.save(e17);
			enrollmentrepo.save(e18);
			enrollmentrepo.save(e19);
			enrollmentrepo.save(e20);
			enrollmentrepo.save(e21);
			enrollmentrepo.save(e22);
			enrollmentrepo.save(e23);
			enrollmentrepo.save(e24);
			enrollmentrepo.save(e25);
			enrollmentrepo.save(e26);
			enrollmentrepo.save(e27);
			enrollmentrepo.save(e28);
			enrollmentrepo.save(e29);
			enrollmentrepo.save(e30);
			enrollmentrepo.save(e31);
			enrollmentrepo.save(e32);
			enrollmentrepo.save(e33);
			enrollmentrepo.save(e34);
			enrollmentrepo.save(e35);
			enrollmentrepo.save(e36);
			enrollmentrepo.save(e37);
			enrollmentrepo.save(e38);
			enrollmentrepo.save(e39);
			enrollmentrepo.save(e40);
			enrollmentrepo.save(e41);
			enrollmentrepo.save(e42);
			enrollmentrepo.save(e43);
			enrollmentrepo.save(e44);
			enrollmentrepo.save(e45);
			enrollmentrepo.save(e46);
			enrollmentrepo.save(e47);
			enrollmentrepo.save(e48);
			enrollmentrepo.save(e49);
			enrollmentrepo.save(e50);
			enrollmentrepo.save(e51);
			enrollmentrepo.save(e52);
			enrollmentrepo.save(e53);
			enrollmentrepo.save(e54);
			enrollmentrepo.save(e55);
			enrollmentrepo.save(e56);
			enrollmentrepo.save(e57);
			enrollmentrepo.save(e58);
			enrollmentrepo.save(e59);
			enrollmentrepo.save(e60);
			enrollmentrepo.save(e61);
			enrollmentrepo.save(e62);
			enrollmentrepo.save(e63);
			enrollmentrepo.save(e64);
			enrollmentrepo.save(e65);
			enrollmentrepo.save(e66);
			enrollmentrepo.save(e67);
			enrollmentrepo.save(e68);
			enrollmentrepo.save(e69);
			enrollmentrepo.save(e70);
			enrollmentrepo.save(e71);
			enrollmentrepo.save(e72);
			enrollmentrepo.save(e73);
			enrollmentrepo.save(e74);
			enrollmentrepo.save(e75);
			log.info("***** DATABASE INITIALIZATION END *****");
		};
	}
}