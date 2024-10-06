/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Huong
 */
import java.time.LocalDate;

public class Lending implements Comparable<Lending> {

    private String bcode;
    private String rcode;
    private LocalDate ldate;
    private LocalDate rdate;
    private int state;  // 1: book is still with the reader, 2: book is returned

    public Lending(String bcode, String rcode) {
        this.bcode = bcode;
        this.rcode = rcode;
        this.ldate = LocalDate.now();
        this.rdate = null;
        this.state = 1;
    }

    public String getBcode() {
        return bcode;
    }

    public String getRcode() {
        return rcode;
    }

    public LocalDate getLdate() {
        return ldate;
    }

    public LocalDate getRdate() {
        return rdate;
    }

    public int getState() {
        return state;
    }

    public void setRdate(LocalDate rdate) {
        this.rdate = rdate;
        this.state = 2; // return;
    }

    public void setLdate(LocalDate ldate) {
        this.ldate = ldate;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return bcode + "," + rcode + "," + ldate + "," + rdate + "," + state;
    }

    @Override
    public int compareTo(Lending o) {
        int bcodeComparison = this.bcode.compareToIgnoreCase(o.getBcode());
        if (bcodeComparison == 0) {
            return this.rcode.compareToIgnoreCase(o.getRcode());
        }
        return bcodeComparison;
    }

}
