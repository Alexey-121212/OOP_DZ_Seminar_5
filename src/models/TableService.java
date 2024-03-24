package models;

import presenters.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public class TableService implements Model {

    private Collection<Table> tables;

    @Override
    public Collection<Table> loadTables() {
        if (tables == null) {
            tables = new ArrayList<>();

            tables.add(new Table());
            tables.add(new Table());
            tables.add(new Table());
            tables.add(new Table());
            tables.add(new Table());
        }

        return tables;
    }

    @Override
    public int reservationTable(Date reservationDate, int tableNo, String name) {
        for (Table table : tables) {
            if (table.getNo() == tableNo) {
                Reservation reservation = new Reservation(table, reservationDate, name);
                table.getReservations().add(reservation);
                return reservation.getId();
            }
        }
        throw new RuntimeException("Некорректный номер столика");
    }

    /**
     *
     * Поменять бронь столика
     * @param oldReservation номер старого резерва (для снятия)
     * @param reservationDate дата резерва столика
     * @param tableNo номер столика
     * @param name Имя
     */
    @Override
    public int changeReservationTable(int oldReservation, Date reservationDate, int tableNo, String name){
        for (Table table: tables) {
            Optional<Reservation> reservation = table.getReservations().stream().filter(r -> r.getId() == oldReservation).findFirst();
            if (reservation.isPresent())
            {
                table.getReservations().remove(reservation.get());
                return reservationTable(reservationDate, tableNo, name);
            }
        }
        throw new RuntimeException("Некорректный номер брони");
    }
}
