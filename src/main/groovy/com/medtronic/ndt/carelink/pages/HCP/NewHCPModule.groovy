package com.medtronic.ndt.carelink.pages.HCP

import geb.Module
import com.medtronic.ndt.carelink.data.HCP

class NewHCPModule extends Module{

    private static List<String> names = ['Akila', 'Anu', 'Jamie', 'Paul', 'Sean', 'Katie', 'Sri', 'Shelly']
    static content = {
        userName{$ "input", id: "userform:username"}
        firstName {$ "input", id: "userform:firstname"}
        lastname {$"input", id: "userform:lastname"}
        email{$"input", id: "userform:email"}
        password{$"input", id: "userform:password"}
        confirmPassword {$ "input", id: "userform:passwordConfirm"}
        securityAnswer{$ "input", id: "userform:answer"}
        previlege{$ "input", id: "userform:previlege"}
        saveBtn {$"input", id: "userform:save"}
        continueBtn {$ "input", id: "userform:submit"}
    }

    static HCP generateRandomHCP() {
        String firstname = names[(Math.random()*names.size()).toInteger()]
        String lastname = names[(Math.random()*names.size()).toInteger()]
        new HCP(username: "${firstname}${(Math.random()*Integer.MAX_VALUE).toInteger()}", password: 'testP@ssw0rd!', firstname: firstname,
                lastname: lastname, email: "${firstname}${(Math.random()*Integer.MAX_VALUE).toInteger()}@email.com")
    }
}
