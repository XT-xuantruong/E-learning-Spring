package backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.dao.LectureDAO;
import backend.entity.Lecture;

@Service
@Transactional
public class LectureServiceImpl implements LectureService{
	@Autowired
    private LectureDAO lectureDAO;

	@Override
	public List<Lecture> readListLecture() {
		return lectureDAO.readListLecture();
	}

	@Override
	public Lecture findById(String id) {
		return lectureDAO.findById(id);
	}

	@Override
	public void createLecture(Lecture theLecture) {
		lectureDAO.createLecture(theLecture);
		
	}

	@Override
	public void updateLecture(Lecture theLecture) {
		lectureDAO.updateLecture(theLecture);
		
	}

	@Override
	public void deleteById(String id) {
		lectureDAO.deleteById(id);
	}

}
