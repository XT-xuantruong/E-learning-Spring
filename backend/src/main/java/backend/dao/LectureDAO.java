package backend.dao;

import java.util.List;

import backend.entity.Lecture;

public interface LectureDAO {
	public List<Lecture> readListLecture();
    public Lecture findById(String id);
    public void createLecture(Lecture theLecture);
    public void updateLecture(Lecture theLecture);
    public void deleteById(String id);
}
