package sele8

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait

import java.time.Duration

class SeleniumAutomatorService {

    // Grails inyectará automáticamente este servicio donde lo llames
    void ejecutarAutomatizacion() {
        println "=== Iniciando automatización con Firefox ==="

        // 1. Configurar GeckoDriver automáticamente
        WebDriverManager.firefoxdriver().setup()

        // 2. Opciones de Firefox
        FirefoxOptions options = new FirefoxOptions()
        // Opcional: options.addArguments("-headless") // Si no quieres que se abra la ventana visual

        WebDriver driver = null

        try {
            // 3. Inicializar el navegador
            driver = new FirefoxDriver(options)
            WebDriverWait wait = new WebDriverWait(driver, 10)
            WebDriverWait wait2 = new WebDriverWait(driver,15)
            WebDriverWait wait3 = new WebDriverWait(driver, 20)

            // 4. Tu lógica de negocio / Web Scraping / Automatización
            driver.get("http://127.0.0.1:8085/login/login")
            WebElement button = driver.findElement(By.id("ingresar"))
            button.click()

            //login

            def inputTexto = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("login"))
            )
            inputTexto.sendKeys("cpaz")

            def inputTexto2 = wait2.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("pass"))
            )
            inputTexto2.sendKeys("123")

            def inputTexto3 = wait3.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("btn-login"))
            )
            inputTexto3.click()

            //perfiles

            driver.get("http://127.0.0.1:8085/login/perfiles")

            WebElement combo = driver.findElement(By.id("prfl"))

            Select seleccionar = new Select(combo)

            seleccionar.selectByVisibleText("Contrataciones")

            WebElement button2 = driver.findElement(By.id("btnPerfiles"))
            button2.click()

            //contratos

            driver.get("http://127.0.0.1:8085/contrato/verContrato")

            def botonListaContratos = wait3.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("btn-lista"))
            )

            botonListaContratos.click()

            def buscarContrato= wait2.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("criterioCriterio"))
            )
            buscarContrato.sendKeys("005-DCP-2026")

            def clicBUscarContrato= wait2.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("cnsl-contratos"))
            )
            clicBUscarContrato.click()

            def seleccionarContrato= wait3.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("reg_0"))
            )
            seleccionarContrato.click()


            // Aquí puedes agregar clics, interactuar con formularios, etc.

        } catch (Exception e) {
            println "Ocurrió un error en Selenium: ${e.message}"
            e.printStackTrace()
        } finally {
            // 5. Asegurar el cierre del proceso de Firefox
            if (driver != null) {
                driver.quit()
                println "=== Navegador Firefox cerrado correctamente ==="
            }
        }
    }
}
