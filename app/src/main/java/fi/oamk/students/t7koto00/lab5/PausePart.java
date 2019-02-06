package fi.oamk.students.t7koto00.lab5;

import java.io.Serializable;

public class PausePart extends WorkoutPartBase implements Serializable {
    private int length;

    public PausePart(int localLength)
    {
        length = localLength;
    }
    @Override
    public String getName() {
        return "Pause";
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
