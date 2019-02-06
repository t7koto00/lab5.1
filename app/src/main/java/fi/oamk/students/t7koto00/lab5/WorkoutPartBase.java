package fi.oamk.students.t7koto00.lab5;

import java.io.Serializable;

public abstract class WorkoutPartBase implements Serializable {
    abstract public String getName();
    abstract public int getLength();
    abstract public void setLength(int length);
}
