package test.account;

enum Currency {
    RUB(1),
    USD(57),
    EUR(62);

    public final double rate;

    Currency(double rate) {
        this.rate = rate;
    }
}
