package com.medtronic.ndt.carelink.data

class HCP {
    String username
    String password
    String firstname
    String lastname
    String email

    @Override
    String toString() {
        this.properties.findAll { it.key != 'class' }
    }
}