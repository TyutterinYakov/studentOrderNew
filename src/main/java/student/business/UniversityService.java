package student.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import student.dao.UniversityRepository;
import student.domain.University;
import student.exception.DataNotFoundException;

@Service
public class UniversityService {
	
	@Autowired
	private UniversityRepository uniDao;
	
	
	@Transactional
	public List<University> findAllUniversity(){
		return uniDao.findAll();
	}
	
	@Transactional
	public void removeUniversityById(Long id) {
		uniDao.deleteById(id);
	}
	@Transactional
	public void saveUniversity(University uni) {
		uniDao.save(uni);
	}
	@Transactional
	public University getUniversityById(Long id) throws DataNotFoundException {
		return uniDao.findById(id).orElseThrow(()->
			new DataNotFoundException("Университет с таким id не найден"));
	}


	
}
