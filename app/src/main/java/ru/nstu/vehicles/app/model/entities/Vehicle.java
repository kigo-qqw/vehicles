package ru.nstu.vehicles.app.model.entities;

import java.util.UUID;

public abstract class Vehicle implements IBehavior {
    private UUID uuid;
    private double x;
    private double y;
    private double dx;
    private double dy;
    private long birthTime;
    private long lifeTime;
    private final int habitatWidth;
    private final int habitatHeight;

    public Vehicle(double x, double y, double dx, double dy, long birthTime, long lifeTime, int habitatWidth, int habitatHeight) {
        this.uuid = UUID.randomUUID();
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.birthTime = birthTime;
        this.lifeTime = lifeTime;
        this.habitatWidth = habitatWidth;
        this.habitatHeight=habitatHeight;
    }

    public void move() {
        if (this.x + this.dx > this.habitatWidth) {
            this.dx = -this.dx;
        }
        if (this.y + this.dy > this.habitatHeight) {
            this.dy = -this.dy;
        }

        this.x += this.dx;
        this.y += this.dy;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDx() {
        return this.dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return this.dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
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
