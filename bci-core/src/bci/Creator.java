package bci;

import bci.work.Work;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Creator implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 202510031527L;

    /** Creator's works. */
    private final Map<Integer,Work> _creatorWorks = new TreeMap<>();

    /** Creator's name. */
    private final String _creatorName;

    /** Number of works by this creator. */
    private int _nWorks;


    public Creator (String creatorName) {
        _creatorName = creatorName;
    }


    public int getNWorks() {
        return _nWorks;
    }


    public Map<Integer, Work> getCreatorWorks() {
        return _creatorWorks;
    }


    public String getCreatorName(){
        return _creatorName;
    }


    public void addWork(Work work) {
        _creatorWorks.put(work.getWorkID(), work);
        _nWorks = _creatorWorks.size();
    }

    
    public void removeWork(int workId) {
        _creatorWorks.remove(workId);
        _nWorks = _creatorWorks.size();
    }


    @Override
    public String toString() {
        return _creatorName;
    }
}
