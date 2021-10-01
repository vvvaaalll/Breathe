package vloboda.myProject.breathe;


public class User {

    long daily, price, date, month, year;

    public long getDaily() {
        return daily;
    }

    public void setDaily(long daily) {
        this.daily = daily;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getMonth() {
        return month;
    }

    public void setMonth(long month) {
        this.month = month;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public User(long date, long month, long year) {
        this.date = date;
        this.month = month;
        this.year = year;
    }

    public User() {
    }

    public User(long daily, long price, long date, long month, long year) {
        this.daily = daily;
        this.price = price;
        this.date = date;
        this.month = month;
        this.year = year;
    }
}
