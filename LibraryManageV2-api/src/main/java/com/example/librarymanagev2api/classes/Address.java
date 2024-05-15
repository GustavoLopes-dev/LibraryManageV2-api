package com.example.librarymanagev2api.classes;

public class Address {
    private String state;
    private String city;
    private String neighborhood;
    private String street;

    // Construtor privado para evitar instanciação direta
    private Address() {
    }

    // Métodos getters para os campos da classe Address

    public static class Builder {
        private String state;
        private String city;
        private String neighborhood;
        private String street;
        private String postalCode;

        public Builder() {
            // inicialização opcional de valores padrão
        }

        public Builder withState(String state) {
            this.state = state;
            return this;
        }

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Builder withNeighborhood(String neighborhood) {
            this.neighborhood = neighborhood;
            return this;
        }

        public Builder withStreet(String street) {
            this.street = street;
            return this;
        }

        public Builder withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Address build() {
            Address address = new Address();
            address.setState(this.state);
            address.setCity(this.city);
            address.setNeighborhood(this.neighborhood);
            address.setStreet(this.street);
            return address;
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
