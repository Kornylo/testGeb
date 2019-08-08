/*
	This is the Geb configuration file.

	See: http://www.gebish.org/manual/current/#configuration
*/

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.remote.DesiredCapabilities

import org.openqa.selenium.remote.RemoteWebDriver


waiting {
    timeout = 60
    retryInterval = 1
}

Closure<ChromeDriver> defaultChrome = {
    ChromeOptions chromeOptions = new ChromeOptions()
    // Disable Chrome PDF rendering
    HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>()
    chromeOptionsMap.put("plugins.plugins_disabled", ["Chrome PDF Viewer"])
    chromeOptionsMap.put("plugins.always_open_pdf_externally", true)
    chromeOptionsMap.put("download.default_directory", "${System.properties.'user.dir'}/build/")
    chromeOptionsMap.put("download.prompt_for_download", false)
    chromeOptionsMap.put("download.directory_upgrade", true)
    chromeOptions.setExperimentalOption("prefs", chromeOptionsMap)
    // Start in full screen
    chromeOptions.addArguments("start-maximized")
    new ChromeDriver(chromeOptions)
}

driver = {
    defaultChrome()
}

environments {

    // run via “./gradlew chromeTest”
    // See: http://code.google.com/p/selenium/wiki/ChromeDriver
    ie {
        driver = {
            DesiredCapabilities caps = DesiredCapabilities.internetExplorer()
            caps.setCapability("ignoreZoomSetting", true)
            caps.setCapability("requireWindowFocus", true)
            caps.setCapability("nativeEvents", false)
            def dr = new InternetExplorerDriver(caps)
            dr.manage().window().maximize()
            dr
        }
    }
    firefox {
        driver = {
            def dr = new FirefoxDriver()
            dr.manage().window().maximize()
            return dr
        }
    }
    chrome {
        driver = {
            defaultChrome()
        }
    }
    //Credentials for Perfecto
    String perfectoHost = "medtronicweb.perfectomobile.com"
    String perfectoSecurityToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJrdENITGYxcDVFZEVkelN5M3NzdGxFTlByeEtKQnI5Vy1pQUZzazgzSWVVIn0.eyJqdGkiOiJiMTEyYzAxOS1hMDI3LTQ2M2YtODQ0MS1jMzJiOWJhYjY4NTkiLCJleHAiOjAsIm5iZiI6MCwiaWF0IjoxNTYyNTkyMjY4LCJpc3MiOiJodHRwczovL2F1dGgucGVyZmVjdG9tb2JpbGUuY29tL2F1dGgvcmVhbG1zL21lZHRyb25pY3dlYi1wZXJmZWN0b21vYmlsZS1jb20iLCJhdWQiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsInN1YiI6ImY5MGE0NGEzLTRjZTItNGE2Yi05OWQ3LTQ4MjUzNWViMzFhYiIsInR5cCI6Ik9mZmxpbmUiLCJhenAiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsIm5vbmNlIjoiN2VmZjI1ZjktMzQ0OS00YTNlLThiZjgtNWY5YmMxNGMwNjE5IiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiNzY0ZTI2MTYtMTg2YS00MjE4LTg3NGUtZTU0ZmJlZWVmYjI1IiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19fQ.O6JJYH6FpQym6V4Ld1VqWUfKWzpfcKbOgeR2RgtrYQlcoiHuiQ2nEr1YlebudzX4ZHm8tULWV7rgmGFFI06CY44V5gFaMnYUPndIrAS3SsUsA7NOCsPKyCauzrpwxfNxMI8dJ-t0FStwn6pAtPUGNP_dDk-k3uXkACb5w9L8vW6YYLf1jDqM6NxpBukCGTBKSgeeZa-5eL4tZw_hMF6CCiT25IQ_kJYvCwPU6G27_LsA0xdSQxY0uQuSFBbPrl3RuqpGBFgzuOAgZ1AsurJiMsWfSJyl7WRtupDlEVWo_1YX9OdrA2HWjXMQyT1_2PZazXxTOMIKmd_4klNZusy8DQ"
    String perfectoUser = "dmytro.stadnik@globallogic.com "

    Win10_Firefox {
        driver = {
            DesiredCapabilities capabilities = new DesiredCapabilities()
            capabilities.setCapability("platformName", "Windows")
            capabilities.setCapability("platformVersion", "10")
            capabilities.setCapability("browserName", "Firefox")
            capabilities.setCapability("browserVersion", "67")
            capabilities.setCapability("location", "US East")
            capabilities.setCapability("resolution", "1280x1024")
            capabilities.setCapability("seleniumVersion", "3.4.0")
            capabilities.setCapability("user", perfectoUser)
            capabilities.setCapability("securityToken",perfectoSecurityToken)

            URL url = new URL("https://" + perfectoHost + "/nexperience/perfectomobile/wd/hub/fast")
            RemoteWebDriver dr = new RemoteWebDriver(url, capabilities)
            dr.manage().window().maximize()
            return dr
        }
    }

    Win10_IE11 {
        driver = {
            DesiredCapabilities capabilities = new DesiredCapabilities()
            capabilities.setCapability("platformName", "Windows")
            capabilities.setCapability("platformVersion", "10")
            capabilities.setCapability("browserName", "Internet Explorer")
            capabilities.setCapability("browserVersion", "11")
            capabilities.setCapability("location", "US East")
            capabilities.setCapability("resolution", "1280x1024")
            capabilities.setCapability("ignoreZoomSetting", true)
            capabilities.setCapability("requireWindowFocus", true)
            capabilities.setCapability("nativeEvents", false)
            capabilities.setCapability("iedriverVersion", "3.141")
            capabilities.setCapability("user", perfectoUser)
            capabilities.setCapability("securityToken",perfectoSecurityToken)

            URL url = new URL("https://" + perfectoHost + "/nexperience/perfectomobile/wd/hub/fast")
            RemoteWebDriver dr = new RemoteWebDriver(url, capabilities)
            dr.manage().window().maximize()
            return dr
        }
    }

    Win10_Edge {
        driver = {
            DesiredCapabilities capabilities = new DesiredCapabilities()
            capabilities.setCapability("platformName", "Windows")
            capabilities.setCapability("platformVersion", "10")
            capabilities.setCapability("browserName", "Edge")
            capabilities.setCapability("browserVersion", "18")
            capabilities.setCapability("resolution", "1280x1024")
            capabilities.setCapability("location", "US East")
            capabilities.setCapability("user", perfectoUser)
            capabilities.setCapability("securityToken",perfectoSecurityToken)

            URL url = new URL("https://" + perfectoHost + "/nexperience/perfectomobile/wd/hub/fast")
            RemoteWebDriver dr = new RemoteWebDriver(url, capabilities)
            dr.manage().window().maximize()
            return dr
        }
    }
    Win10_Chrome {
        driver = {
            DesiredCapabilities capabilities = new DesiredCapabilities()
            capabilities.setCapability("platformName", "Windows")
            capabilities.setCapability("platformVersion", "10")
            capabilities.setCapability("browserName", "Chrome")
            capabilities.setCapability("browserVersion", "73")
            capabilities.setCapability("resolution", "1280x1024")
            capabilities.setCapability("location", "US East")
            capabilities.setCapability("user", perfectoUser)
            capabilities.setCapability("securityToken",perfectoSecurityToken)

            URL url = new URL("https://" + perfectoHost + "/nexperience/perfectomobile/wd/hub/fast")
            RemoteWebDriver dr = new RemoteWebDriver(url, capabilities)
            dr.manage().window().maximize()
            return dr
        }
    }

    Mac_Safari12 {
        driver = {
            DesiredCapabilities capabilities = new DesiredCapabilities()
            capabilities.setCapability("platformName", "Mac")
            capabilities.setCapability("platformVersion", "macOS Mojave")
            capabilities.setCapability("browserName", "Safari")
            capabilities.setCapability("browserVersion", "12")
            capabilities.setCapability("resolution", "1280x1024")
            capabilities.setCapability("location", "NA-US-BOS")
            capabilities.setCapability("user", perfectoUser)
            capabilities.setCapability("securityToken",perfectoSecurityToken)

            URL url = new URL("https://" + perfectoHost + "/nexperience/perfectomobile/wd/hub/fast")
            RemoteWebDriver dr = new RemoteWebDriver(url, capabilities)
            dr.manage().window().maximize()
            return dr
        }
    }

}

baseUrl = System.getProperty('carelinkDevUrl')
//spockReports {
//    set 'com.athaydes.spockframework.report.showCodeBlocks': true
//    set 'com.athaydes.spockframework.report.outputDir': 'target/spock-reports'
//}

reportsDir = "target/geb-reports"

atCheckWaiting = true