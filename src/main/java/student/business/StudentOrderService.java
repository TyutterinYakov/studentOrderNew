package student.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import student.domain.Street;
import student.domain.StudentOrder;
import student.domain.StudentOrderChild;

@Service
public class StudentOrderService {
	
	private static final Logger logger = LoggerFactory.getLogger(StudentOrderService.class);
	
	@Autowired
	private StudentOrderRepository orderDao;
	@Autowired
	private StudentOrderChildRepository childDao;
	@Autowired
	private StreetRepository streetDao;
	@Autowired
	private StudentOrderStatusRepository statusDao;
	@Autowired
	private PassportOfficeRepository passportDao;
	@Autowired
	private UniversityRepository universityDao;
	@Autowired 
	private RegisterOfficeRepository registerDao;
	
	
	
	@Transactional
	public void testSave() {
		StudentOrder so = new StudentOrder();
		so.setHusband(buildPerson(false));
		so.setWife(buildPerson(true));
		so.setStudentOrderDate(LocalDateTime.now());
		so.setStatus(statusDao.getOne(1L));
		so.setCertificateNumber("1234567890");
		so.setRegisterOffice(registerDao.getOne(1L));
		so.setMarriageDate(LocalDate.now());
		StudentOrderChild ch = buildChild(so);
		List<StudentOrderChild> childs = new LinkedList<>();
		childs.add(ch);
		so.setStudentOrderChild(childs);
		
		childDao.save(ch);
		orderDao.save(so);
	}
	
	@Transactional
	public void testGet() {
		
		List<StudentOrder> orders = orderDao.findAll();
		logger.info("testGet");
		logger.info("WIFE: "+orders.get(0).getWife().getSurName());
		logger.info("CHILD: "+orders.get(0).getStudentOrderChild().get(0).getGivenName());
	}
	
	private Adult buildPerson(boolean wife) {
		Adult a = new Adult();
		Address ad = new Address();
		Street one = streetDao.getOne(2L);
		ad.setStreet(one);
		ad.setPostCode("3333");
		ad.setBuilding("3A");
		ad.setExtension("4");
		ad.setApartment("237");
		
		if(wife) {
			a.setGivenName("...");
			a.setSurName("Тюттерина");
			a.setPatronymic("...");
			a.setPassportSeria("3333");
			a.setPassportNumber("111111");
			a.setAddress(ad);
			a.setDateOfBirth(LocalDate.now());
			a.setIssueDate(LocalDate.now());
			a.setPassportOffice(passportDao.getOne(1L));
			a.setStudentNumber("11111");
			a.setUniversity(universityDao.getOne(1L));
			
		} else {
			a.setGivenName("Яков");
			a.setSurName("Тюттерин");
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
	
	private StudentOrderChild buildChild(StudentOrder so) {
		StudentOrderChild ch = new StudentOrderChild();
		ch.setSurName("Тюттерин");
		ch.setGivenName("...");
		ch.setPatronymic("Яковлевич");
		ch.setChildCertificate("333333");
		ch.setCertificateDate(LocalDate.now());
		ch.setDateOfBirth(LocalDate.now());
		ch.setStudentOrder(orderDao.getOne(1L));
		Address add = new Address();
		add.setStreet(streetDao.getOne(1L));
		add.setPostCode("33333");
		add.setBuilding("22");
		add.setExtension("4A");
		add.setApartment("33");
		ch.setAddress(add);
		ch.setRegisterOffice(registerDao.getOne(2L));		
		
		return ch;
	}
}