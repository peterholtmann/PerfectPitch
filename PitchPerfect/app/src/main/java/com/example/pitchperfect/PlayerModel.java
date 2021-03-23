package com.example.pitchperfect;

public class PlayerModel
{
    private int player_id;
    private String name;
    private float speed;
    private float time_to_plate;
    private float rpm;

    public PlayerModel(int player_id, String name, float speed, float time_to_plate, float rpm)
    {
        this.player_id = player_id;
        this.name = name;
        this.speed = speed;
        this.time_to_plate = time_to_plate;
        this.rpm = rpm;
    }

    public PlayerModel(){}

    @Override
    public String toString() {
        return "PlayerModel{" +
                "player_id=" + player_id +
                ", name=" + name +
                ", speed=" + speed +
                ", time_to_plate=" + time_to_plate +
                ", rpm=" + rpm +
                '}';
    }

    public int getPlayer_id()
    {
        return player_id;
    }

    public int setPlayer_id(int player_id)
    {
        this.player_id = player_id;
        return this.player_id;
    }

    public String getName()
    {
        return name;
    }

    public String setName(String name)
    {
        this.name = name;
        return this.name;
    }

    public float getSpeed()
    {
        return speed;
    }

    public float setSpeed(float speed)
    {
        this.speed = speed;
        return this.speed;
    }

    public float getTime_to_plate()
    {
        return time_to_plate;
    }

    public float setTime_to_plate(float time_to_plate)
    {
        this.time_to_plate = time_to_plate;
        return this.time_to_plate;
    }

    public float getRpm()
    {
        return rpm;
    }

    public float setRpm(float rpm)
    {
        this.rpm = rpm;
        return this.rpm;
    }
}
