package fi.oamk.students.t7koto00.lab5;

import java.io.Serializable;

public class WorkoutPart extends WorkoutPartBase implements Serializable {
    private int length;

    public WorkoutPart(int localLength)
    {
    length = localLength;
    }
    @Override
    public String getName() {
        return "Workout";
    }

    @Override
    public void setLength(int localLength) {
       length = localLength;
    }
    @Override
    public int getLength() {
        return length;
    }
}
