package com.kornylo.test.geb.pages

import geb.Page

class GoogleTestPage extends Page {


    // Synchronization check, gets looped for the duration of the configured wait timeout (see: GebConfig) until this resolves true
    static at = {
        waitFor {
            searchButton.displayed
        }
    }

    static content = {
        // Username
        searchField(required: false) { $("#tsf > div:nth-child(2) > div.A8SBwf > div.FPdoLc.VlcLAe > center > input.gNO89b") }
        searchButton(required: false) { $('#tsf > div:nth-child(2) > div.A8SBwf > div.RNNXgb > div > div.a4bIc > input') }
        sss (required: false){$('#tsf > div:nth-child(2) > div.A8SBwf > div.RNNXgb > div > div.a4bIc > input')}
        search(required: false){$('#tsf > div:nth-child(2) > div.A8SBwf > div.FPdoLc.VlcLAe > center > input.gNO89b')}
        result{$('#rso > div:nth-child(1) > div > div > div > div > div.r > a > h3')}
    }

    public void printPageTitle() {
        driver.getTitle()

    }
    boolean searchButton() {
        searchField.displayed
    }
void saearch (String string){
    sss = string
}
    void button () {
        search.click()
    }
    public verifyTextAfterSearch(String Text) {
        waitFor { result.displayed }
        assert result.text().contains(Text)
    }

}
