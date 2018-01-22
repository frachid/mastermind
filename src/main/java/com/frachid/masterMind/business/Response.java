package com.frachid.masterMind.business;

public class Response {

    private int exact = 0;
    private int inexact = 0;

    public int getInexact() {
        return inexact;
    }
    public void setInexact(int inexact) {
        this.inexact = inexact;
    }
    public int getExact() {
        return exact;
    }
    public void setExact(int exact) {
        this.exact = exact;
    }

    public Response(int exact, int inexact) {
        this.exact = exact;
        this.inexact = inexact;
    }

    @Override
    public String toString() {
        return exact+"V"+inexact+"X";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Response response = (Response) o;

        if (exact != response.exact)
            return false;
        return inexact == response.inexact;
    }

    @Override
    public int hashCode() {
        int result = exact;
        result = 31 * result + inexact;
        return result;
    }
}
