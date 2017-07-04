package test.tx;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

abstract class FlyBooking {
    JdbcTemplate jdbc;

    @Transactional(isolation = Isolation.DEFAULT)
    boolean tryBookFly(int flyNumber) {
        int passengersCount = jdbc.queryForObject(
                "select count(id) from passengers where fly_num = ?",
                Integer.class,
                flyNumber);
        if (passengersCount > 100) {
            reserveSeats(flyNumber, passengersCount);
            jdbc.update("update passengers set seat_reserved=1 where fly_num = ?", flyNumber);
            jdbc.update("update flies set closed=1 where fly_num = ?", flyNumber);
            return true;
        }
        return false;
    }

    abstract void reserveSeats(int flyNumber, int seatNumber);
}

