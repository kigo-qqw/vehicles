package ru.nstu.vehicles.app.model.entities;

import java.util.UUID;

public abstract class Vehicle implements IBehavior {
    private UUID uuid;
    private int x;
    private int y;
    private long birthTime;
    private long lifeTime;

    public Vehicle(int x, int y, long birthTime, long lifeTime) {
        this.uuid = UUID.randomUUID();
        this.x = x;
        this.y = y;
        this.birthTime = birthTime;
        this.lifeTime = lifeTime;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public long getBirthTime() {
        return this.birthTime;
    }

    public void setBirthTime(long birthTime) {
        this.birthTime = birthTime;
    }

    public long getLifeTime() {
        return this.lifeTime;
    }

    public void setLifeTime(long lifeTime) {
        this.lifeTime = lifeTime;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
