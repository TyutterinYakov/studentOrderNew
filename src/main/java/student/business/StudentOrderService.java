package student.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import student.dao.PassportOfficeRepository;
import student.dao.RegisterOfficeRepository;
import student.dao.StreetRepository;
import student.dao.StudentOrderChildRepository;
import student.dao.StudentOrderRepository;
import student.dao.StudentOrderStatusRepository;
import student.dao.UniversityRepository;
import student.domain.Address;
import student.domain.Adult;
import student.domain.Child;
import student.domain.Street;
import student.domain.StudentOrder;
import student.domain.StudentOrderChild;

@Service
public class StudentOrderService {
	
//	private static final Logger logger = LoggerFactory.getLogger(StudentOrderService.class);
	
	//Класс не имеет никакой логической нагрузки. Создан в самом начале и не используется.
	
	private final StudentOrderRepository orderDao;
	private final StudentOrderChildRepository childDao;
	private final StreetRepository streetDao;
	private final StudentOrderStatusRepository statusDao;
	private final PassportOfficeRepository passportDao;
	private final UniversityRepository universityDao;
	private final RegisterOfficeRepository registerDao;
	
	@Autowired
	public StudentOrderService(StudentOrderRepository orderDao, StudentOrderChildRepository childDao,
			StreetRepository streetDao, StudentOrderStatusRepository statusDao, PassportOfficeRepository passportDao,
			UniversityRepository universityDao, RegisterOfficeRepository registerDao) {
		super();
		this.orderDao = orderDao;
		this.childDao = childDao;
		this.streetDao = streetDao;
		this.statusDao = statusDao;
		this.passportDao = passportDao;
		this.universityDao = universityDao;
		this.registerDao = registerDao;
	}


	@Transactional
	public void testSave() {
		StudentOrder so = new StudentOrder();

		so.setStudentOrderDate(LocalDateTime.now());
		so.setStatus(statusDao.getOne(1L));
		so.setCertificateNumber("1234567890");
		so.setRegisterOffice(registerDao.findById(1L).orElseGet(null));
		so.setMarriageDate(LocalDate.now());
		so.setHusband(buildPerson(false));
		so.setWife(buildPerson(true));
		List<StudentOrderChild> ch = buildChild(so);
		so.setStudentOrderChild(ch);
		orderDao.save(so);
		
		System.out.println(ch.size());
		for(int i=0; i<ch.size(); i++) {
		childDao.save(ch.get(i));
		}
	}
	
	
	private Adult buildPerson(boolean wife) {
		Adult a = new Adult();
		
		if(wife) {
			Address ad = new Address();
			Street one = streetDao.findById(1L).get();
			ad.setStreet(one);
			ad.setPostCode("3333");
			ad.setBuilding("3A");
			ad.setExtension("4");
			ad.setApartment("237");
			a.setGivenName("Ирина");
			a.setSurName("Макарова");
			a.setPatronymic("Сергеевна");
			a.setPassportSeria("3333");
			a.setPassportNumber("111111");
			a.setAddress(ad);
			a.setDateOfBirth(LocalDate.now());
			a.setIssueDate(LocalDate.now());
			a.setPassportOffice(passportDao.getOne(1L));
			a.setStudentNumber("11111");
			a.setUniversity(universityDao.getOne(1L));
			
			
		} else {
			Address ad = new Address();
			Street one = streetDao.findById(1L).get();
			ad.setStreet(one);
			ad.setPostCode("3443");
			ad.setBuilding("3");
			ad.setExtension("4");
			ad.setApartment("23");
			a.setGivenName("Аркадий");
			a.setSurName("Макаров");
			a.setPatronymic("Николаевич");
			a.setPassportSeria("4444");
			a.setPassportNumber("666111");
			a.setDateOfBirth(LocalDate.now());
			a.setIssueDate(LocalDate.now());
			a.setPassportOffice(passportDao.getOne(1L));
			a.setAddress(ad);
			a.setStudentNumber("22222");
			a.setUniversity(universityDao.getOne(1L));
		}
		return a;
	}

	private List<StudentOrderChild> buildChild(StudentOrder so) {
		List<StudentOrderChild> childs = new LinkedList<>();
		StudentOrderChild sc = new StudentOrderChild();
		System.out.println(so.getStudentOrderId());
		sc.setStudentOrder(so);
		
		Child ch = new Child();
		ch.setSurName("Макаров");
		ch.setGivenName("Сергей");
		ch.setPatronymic("Аркадьевич");
		ch.setChildCertificate("333333");
		ch.setCertificateDate(LocalDate.now());
		ch.setDateOfBirth(LocalDate.now());
		
		Address add = new Address();
		add.setStreet(streetDao.getOne(1L));
		add.setPostCode("33333");
		add.setBuilding("22");
		add.setExtension("4A");
		add.setApartment("33");
		ch.setAddress(add);
		ch.setRegisterOffice(registerDao.getOne(2L));		
		sc.setChild(ch);
		sc.setStudentOrder(so);
		childs.add(sc);
		
		StudentOrderChild sc1 = new StudentOrderChild();
		Child ch1 = new Child();
		ch1.setSurName("Макарова");
		ch1.setGivenName("Василиса");
		ch1.setPatronymic("Аркадьевна");
		ch1.setChildCertificate("45678");
		ch1.setCertificateDate(LocalDate.now());
		ch1.setDateOfBirth(LocalDate.now());
		
		Address add1 = new Address();
		add1.setStreet(streetDao.getOne(2L));
		add1.setPostCode("32231");
		add1.setBuilding("2");
		add1.setExtension("1A");
		add1.setApartment("3");
		ch1.setAddress(add1);
		ch1.setRegisterOffice(registerDao.getOne(2L));		
		sc1.setChild(ch1);
		sc1.setStudentOrder(so);
		childs.add(sc1);
		
		return childs;
	}
	
	
}
