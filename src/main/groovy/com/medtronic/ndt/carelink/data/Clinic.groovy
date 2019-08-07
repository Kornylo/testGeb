package com.medtronic.ndt.carelink.data

class Clinic {
    String id
    String name
    String address1
    String address2
    String city
    String state
    String zip
    String phoneNumber
    String country
    String language

    @Override
    String toString() {
        this.properties.findAll {it.key != 'class'}
    }
}
