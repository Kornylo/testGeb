package com.medtronic.ndt.carelink.screenshot

import org.openqa.selenium.OutputType

import javax.imageio.ImageIO

/**
 * Trait for taking screenshots and getting the screen page source (xml)
 */
trait ScreenshotTrait {

    Closure<String> screenshotScriptDefaultFolder = { String feature ->
        "${System.properties.'user.dir'}/build/screenshots/${feature}"
    }

    /**
     * Takes a screenshot
     * @param folder the parent folder of the screenshot file (typically the feature name)
     * @param fileName the name of the screenshot file without the extension (e.g. "myTestFailureScreenshot" NOT "myTestFailureScreenshot.png")
     * @param stringIds a list of ids of the Strings appearing in the screenshot
     */
    void screenshot(String folder, String fileName, List<String> nonUniqueStringIds = []) {
        String folderPath = screenshotScriptDefaultFolder(folder)
        println "Saving screenshot and displayed string ids to ${folderPath} with name ${fileName}"
        def img = ImageIO.read(new ByteArrayInputStream(driver.getScreenshotAs(OutputType.BYTES)))
        def pngFile = new File(folderPath, "${fileName}.png")
        pngFile.mkdirs()
        ImageIO.write(img, "png", pngFile)
    }
}