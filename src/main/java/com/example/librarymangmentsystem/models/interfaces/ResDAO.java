package com.example.librarymangmentsystem.models.interfaces;

import com.example.librarymangmentsystem.models.Reservation;

import java.util.List;

public interface ResDAO {
    public boolean saveReservation(Reservation reservation);
    public void update(Reservation res);
    public Reservation getRes(int id);
    public List<Reservation> getAll();


}
