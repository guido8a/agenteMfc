package com.ejemplo
//
//import org.openqa.selenium.WebDriver
//import org.openqa.selenium.firefox.FirefoxDriver
//import org.openqa.selenium.firefox.FirefoxOptions
//import io.github.bonigarcia.wdm.WebDriverManager
//
//class SeleniumService {
//
//    def ejecutarFirefox(String url) {
//        log.info "Iniciando proceso Selenium con Firefox para la URL: ${url}"
//
//        WebDriverManager.firefoxdriver().setup()
//
//        FirefoxOptions options = new FirefoxOptions()
//        options.addArguments("-headless")
//
//        WebDriver driver = new FirefoxDriver(options)
//        String titulo = ""
//
//        try {
//            driver.get(url)
//            titulo = driver.getTitle()
//            log.info "¡Conexión exitosa! Título capturado: ${titulo}"
//        } catch (Exception e) {
//            log.error "Error en Selenium: ${e.message}", e
//            titulo = "Error: ${e.message}"
//        } finally {
//            driver.quit()
//        }
//        return titulo
//    }
//}


import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.edge.EdgeOptions

import java.nio.file.Paths
import java.util.HashMap
import java.util.Map
import java.util.Arrays

import java.time.Duration
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.edge.EdgeDriver

import org.openqa.selenium.WebDriver
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.remote.DesiredCapabilities
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class SeleniumService {


//    String rutaProyecto = java.lang.System.getProperty("user.dir")
//    String rutaDriver = "${rutaProyecto}/src/main/resources/drivers/msedgedriver"

// 1. Detectar dinámicamente el sistema operativo
    boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win")
    String driverName = isWindows ? "msedgedriver.exe" : "msedgedriver"

// 2. Definir el archivo físico en el directorio temporal usando java.io.File
    String tempDir = System.getProperty("java.io.tmpdir")
    java.io.File targetFile = new java.io.File(tempDir, driverName)

// 3. Validar existencia y extraer usando el método nativo de Groovy

// 4. Configurar la propiedad apuntando a la ruta del archivo extraído
//    System.setProperty("webdriver.edge.driver", targetFile.getAbsolutePath())

    // Grails inyectará automáticamente este servicio donde lo llames
    void ejecutarAutomatizacion(int oferta) {
        println "=== Iniciando automatización con Edge ==="

        if (!targetFile.exists()) {

            // Buscamos el driver dentro de la carpeta resources del WAR
            java.io.InputStream inputStream = this.class.classLoader.getResourceAsStream("drivers/" + driverName)

            if (inputStream == null) {
                throw new java.io.FileNotFoundException("No se encontró el driver dentro del WAR en: resources/drivers/" + driverName)
            }

            // Copia automática y ultra limpia usando la sintaxis nativa de Groovy
            targetFile.withOutputStream { outputStream ->
                outputStream << inputStream
            }

            // Si corre en Linux, le asignamos permisos de ejecución al binario físico
            if (!isWindows) {
                targetFile.setExecutable(true)
            }
        }

//        log.info "Buscando driver en la ruta relativa: ${rutaDriver}"
//        java.lang.System.setProperty("webdriver.edge.driver", rutaDriver)

// 4. Configurar la propiedad apuntando a la ruta física temporal calculada
        System.setProperty("webdriver.edge.driver", targetFile.getAbsolutePath())

        // 2. CONFIGURACIÓN DEL NAVEGADOR EDGE PARA LINUX
        EdgeOptions options = new EdgeOptions()

// 1. Usamos DesiredCapabilities para asegurar compatibilidad con Selenium 3
        DesiredCapabilities capabilities = DesiredCapabilities.edge()

// 2. Declaramos el tipo estrictamente en el diamante <String> para evitar el error del WAR
        java.util.ArrayList<String> argsList = new java.util.ArrayList<String>()
//        argsList.add("--headless")
        argsList.add("--disable-gpu")
        argsList.add("--no-sandbox")
        argsList.add("--start-maximized")
        argsList.add("--disable-dev-shm-usage")
        argsList.add("--window-size=1920,1080")

// 3. Declaramos los tipos estrictamente en el HashMap <String, Object>
        java.util.HashMap<String, Object> edgeOptionsMap = new java.util.HashMap<String, Object>()
        edgeOptionsMap.put("detach", true)
        edgeOptionsMap.put("args", argsList)

// 4. Inyectamos la configuración forzando el tipo Object
        capabilities.setCapability("ms:edgeOptions", (Object) edgeOptionsMap)

        // 1. Configurar GeckoDriver automáticamente
//        WebDriverManager.edgedriver().setup()
//        WebDriver driver = null

//        WebDriver driver = new EdgeDriver(options)
        WebDriver driver = new EdgeDriver(capabilities)

        try {
            // 3. Inicializar el navegador
//            driver = new EdgeDriver(options)
            WebDriverWait wait = new WebDriverWait(driver, 10)
            WebDriverWait wait2 = new WebDriverWait(driver, 15)
            WebDriverWait wait3 = new WebDriverWait(driver, 20)
            WebDriverWait wait4 = new WebDriverWait(driver, 5)

            driver.get("http://localhost:6012/mfc-oa/web/app.php")


            def ingreso = wait2.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("btn_inicio_abajo"))
            )
            ingreso.click()

            // OFERTAS
//
            def abrirOfertas = wait2.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath('//*[@title="Ofertas"]'))
            )
            abrirOfertas.click()

            //OFERTA SELECCIONADA

            if (!verificarSiUrlExiste("http://localhost:6012/mfc-oa/web/app.php/ofertas/edicion/${oferta}")) {
                log.error("El proceso se detuvo: La URL no existe o no responde.")
                driver.get("http://localhost:6012/mfc-oa/web/app.php")
            } else {
                driver.get("http://localhost:6012/mfc-oa/web/app.php/ofertas/edicion/${oferta}")

                //COMPROMISO DE PARTICIPACION

                def ingresoCompromiso = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("btnFormularioCompromisoParticipacion"))
                )
                ingresoCompromiso.click()

                //Agregar

                def agregarPersonal = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("agregarPersonalTecnico"))
                )
                agregarPersonal.click()

                //Hoja de vida

                //tipo de documento
                def comboUno = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("comproPartici_tipoDocumentoCp"))
                )
                Select seleccionarTipoDocumento = new Select(comboUno)
                seleccionarTipoDocumento.selectByVisibleText("CÉDULA")

                //numero documento

                def campoNumeroDocumento = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("comproPartici_numeroDocumentoCp"))
                )
                campoNumeroDocumento.clear()
                campoNumeroDocumento.sendKeys("1716473325")

                //nombre completo

                def campoNombreCompleto = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("comproPartici_nombresCp"))
                )
                campoNombreCompleto.clear()
                campoNombreCompleto.sendKeys("Pedro Perez")

                //Lugar Nacimiento

                def campoLugarNacimiento = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("comproPartici_lugarNacimientoCp"))
                )
                campoLugarNacimiento.clear()
                campoLugarNacimiento.sendKeys("Quito")

                //fecha de nacimiento

                WebElement inputFecha = wait.until(
                        ExpectedConditions.presenceOfElementLocated(By.id("comproPartici_fechaNacimientoCp"))
                )

                JavascriptExecutor js = (JavascriptExecutor) driver

                js.executeScript("arguments[0].removeAttribute('readonly');", inputFecha)

                inputFecha.clear()
                inputFecha.sendKeys("1980/08/13")

                js.executeScript("arguments[0].dispatchEvent(new Event('change'));", inputFecha)
                js.executeScript("arguments[0].dispatchEvent(new Event('blur'));", inputFecha)

                //nacionalidad

                def campoNacionalidad = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("comproPartici_nacionalidadCp"))
                )
                campoNacionalidad.clear()
                campoNacionalidad.sendKeys("Ecuatoriano")

                //nivel de estudio

                def comboNE = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("comproPartici_nivelEstudioCodCp"))
                )
                Select seleccionarNivelEstudio = new Select(comboNE)
                seleccionarNivelEstudio.selectByVisibleText("CUARTO NIVEL")

                //titulacion

                def campoTitulacion = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("comproPartici_tituloProfesionalCp"))
                )
                campoTitulacion.clear()
                campoTitulacion.sendKeys("Ingeniero en computación")

                //fecha de graduacion

                WebElement inputFechaGraduacion = wait.until(
                        ExpectedConditions.presenceOfElementLocated(By.id("comproPartici_fechaGraduacionCp"))
                )

                JavascriptExecutor js2 = (JavascriptExecutor) driver

                js2.executeScript("arguments[0].removeAttribute('readonly');", inputFechaGraduacion)

                inputFechaGraduacion.clear()
                inputFechaGraduacion.sendKeys("2010/05/20")

                js2.executeScript("arguments[0].dispatchEvent(new Event('change'));", inputFechaGraduacion)
                js2.executeScript("arguments[0].dispatchEvent(new Event('blur'));", inputFechaGraduacion)


                //titulo IV nivel

                def campoTitulo = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("comproPartici_tituloCuartoNivCp"))
                )
                campoTitulo.clear()
                campoTitulo.sendKeys("Ingeniero")

                //tiempo de participacion

                def campoTiempoParticipacion = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("comproPartici_tiempoPartiCp"))
                )
                campoTiempoParticipacion.clear()
                campoTiempoParticipacion.sendKeys("6")

                //tiempo de participacion seleccion

                def comboTiempoParticipacion = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("comproPartici_tiempoPartiCpMedida"))
                )
                Select seleccionarTiempoParticipacion= new Select(comboTiempoParticipacion)
                seleccionarTiempoParticipacion.selectByVisibleText("MESES")

                //funcion

                def campoFuncion= wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("comproPartici_cargoConsCp"))
                )
                campoFuncion.clear()
                campoFuncion.sendKeys("Desarrollador Web")

                //observaciones

                def campoObservacion= wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("comproPartici_observacionesCp"))
                )
                campoObservacion.clear()
                campoObservacion.sendKeys("NINGUNA")

                //boton guardar

                def guardarPersonal = wait.until(
                        ExpectedConditions.elementToBeClickable(By.id("guardarPersonalTecnico")) )
                guardarPersonal.click()

                def guardarPersonal2 = wait.until(
                        ExpectedConditions.elementToBeClickable(By.id("guardarPersonalTecnico")) )
                guardarPersonal2.click()

                WebElement botonGuardar = driver.findElement(By.id("guardarPersonalTecnico"))

                JavascriptExecutor js3 = (JavascriptExecutor) driver
                js3.executeScript("arguments.focus();", botonGuardar)
                js3.executeScript("arguments.click();", botonGuardar)

                //siguiente parte



//            //ingresar a los datos del oferente
//
//            def abrirCrearIOferente = wait2.until(
//                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Crear Oferente')]"))
//            )
//            abrirCrearIOferente.click()
//
//            //DATOS DEL OFERENTE
//
//            def abrirAcordeonUno = wait.until(
//                    ExpectedConditions.elementToBeClickable(By.id("tituloSeccionUno")) )
//            abrirAcordeonUno.click()
//
//            //participacion
//
//            def comboUno = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(By.id("incop_mfc_ofertasbundle_tmepoferente_tipoParticipacion"))
//            )
//            Select seleccionarUno = new Select(comboUno)
//            seleccionarUno.selectByVisibleText("Individual")
//
//            //nombre oferente
//
//            def campoNombreOferente = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(By.id("incop_mfc_ofertasbundle_tmepoferente_nombreOferente"))
//            )
//            campoNombreOferente.clear()
//            campoNombreOferente.sendKeys("Pedro")
//
//            //origen
//            def comboDos = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(By.id("incop_mfc_ofertasbundle_tmepoferente_extranjero"))
//            )
//            Select seleccionarDos = new Select(comboDos)
//            seleccionarDos.selectByVisibleText("Nacional")
//
//            //ruc
//            def campoRuc = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(By.id("incop_mfc_ofertasbundle_tmepoferente_documento"))
//            )
//            campoRuc.clear()
//            campoRuc.sendKeys("1111111111")
//
//            //naturaleza
//            def comboTres = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(By.id("incop_mfc_ofertasbundle_tmepoferente_tipoPersona"))
//            )
//            Select seleccionarTres = new Select(comboTres)
//            seleccionarTres.selectByVisibleText("Persona Natural")
//
//            //profesion
//            def campoProfesion = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(By.id("incop_mfc_ofertasbundle_tmepoferente_profesion"))
//            )
//            campoProfesion.clear()
//            campoProfesion.sendKeys("Ingeniero")
//
//            //DOMICILIO DEL OFERENTE
//
//            def abrirAcordeonDos = wait.until(
//                    ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'DOMICILIO DEL OFERENTE')]")) )
//            abrirAcordeonDos.click()
//
//            //provincia
//
//            def comboProvincia = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(By.id("incop_mfc_ofertasbundle_tmepoferente_codigoProvincia"))
//            )
//            Select selectProvincia = new Select(comboProvincia)
//            selectProvincia.selectByVisibleText("Pichincha")
//
//            //canton
//
//            def comboCanton = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(By.id("incop_mfc_ofertasbundle_tmepoferente_codigoCiudad"))
//            )
//            Select selectCanton = new Select(comboCanton)
//            selectCanton.selectByVisibleText("Quito")
//
//            //calle principal
//            def campoCallePrincipal = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(By.id("incop_mfc_ofertasbundle_tmepoferente_callePrincipal"))
//            )
//            campoCallePrincipal.clear()
//            campoCallePrincipal.sendKeys("Calle A")
//
//            //calle numero
//            def campoNumero= wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(By.id("incop_mfc_ofertasbundle_tmepoferente_calleNumero"))
//            )
//            campoNumero.clear()
//            campoNumero.sendKeys("42")
//
//            //calle secundaria
//            def campoCalleSecundaria = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(By.id("incop_mfc_ofertasbundle_tmepoferente_calleSecundaria"))
//            )
//            campoCalleSecundaria.clear()
//            campoCalleSecundaria.sendKeys("Calle B")
//
//            //codigo postal
//            def campoCodigoPostal = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(By.id("incop_mfc_ofertasbundle_tmepoferente_codigoPostal"))
//            )
//            campoCodigoPostal.clear()
//            campoCodigoPostal.sendKeys("170170")
//
//            //telefono
//            def campoTelefono = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(By.id("incop_mfc_ofertasbundle_tmepoferente_telefono"))
//            )
//            campoTelefono.clear()
//            campoTelefono.sendKeys("0987654321")
//
//            //correo
//            def campoCorreo = wait.until(
//                    ExpectedConditions.visibilityOfElementLocated(By.id("incop_mfc_ofertasbundle_tmepoferente_correo"))
//            )
//            campoCorreo.clear()
//            campoCorreo.sendKeys("a@a.com")
//
//

//          sleep(3000)
            }    // Aquí puedes agregar clics, interactuar con formularios, etc.

        } catch (Exception e ) {
            println "Ocurrió un error en Selenium: ${e.message}"
            e.printStackTrace()
        } finally {
            // 5. Asegurar el cierre del proceso de Firefox
            if (driver != null) {
//                driver.quit()
                println "=== Terminado correctamente ==="
            }
        }
    }

    void ejecutarAutomatizacionExperienciaLaboral(int oferta, int personal) {
        println "=== Iniciando automatización con Edge ==="

        if (!targetFile.exists()) {

            java.io.InputStream inputStream = this.class.classLoader.getResourceAsStream("drivers/" + driverName)

            if (inputStream == null) {
                throw new java.io.FileNotFoundException("No se encontró el driver dentro del WAR en: resources/drivers/" + driverName)
            }

            targetFile.withOutputStream { outputStream ->
                outputStream << inputStream
            }

            if (!isWindows) {
                targetFile.setExecutable(true)
            }
        }

        System.setProperty("webdriver.edge.driver", targetFile.getAbsolutePath())

        EdgeOptions options = new EdgeOptions()

        DesiredCapabilities capabilities = DesiredCapabilities.edge()

        java.util.ArrayList<String> argsList = new java.util.ArrayList<String>()
        argsList.add("--disable-gpu")
        argsList.add("--no-sandbox")
        argsList.add("--start-maximized")
        argsList.add("--disable-dev-shm-usage")
        argsList.add("--window-size=1920,1080")

        java.util.HashMap<String, Object> edgeOptionsMap = new java.util.HashMap<String, Object>()
        edgeOptionsMap.put("detach", true)
        edgeOptionsMap.put("args", argsList)

        capabilities.setCapability("ms:edgeOptions", (Object) edgeOptionsMap)

        WebDriver driver = new EdgeDriver(capabilities)

        try {

            WebDriverWait wait = new WebDriverWait(driver, 10)
            WebDriverWait wait2 = new WebDriverWait(driver, 15)
            WebDriverWait wait3 = new WebDriverWait(driver, 20)
            WebDriverWait wait4 = new WebDriverWait(driver, 5)

            driver.get("http://localhost:6012/mfc-oa/web/app.php")


            def ingreso = wait2.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("btn_inicio_abajo"))
            )
            ingreso.click()

            // OFERTAS
//
            def abrirOfertas = wait2.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath('//*[@title="Ofertas"]'))
            )
            abrirOfertas.click()

            //OFERTA SELECCIONADA

            if (!verificarSiUrlExiste("http://localhost:6012/mfc-oa/web/app.php/ofertas/edicion/${oferta}")) {
                log.error("El proceso se detuvo: La URL no existe o no responde.")
                driver.get("http://localhost:6012/mfc-oa/web/app.php")
            } else {
                driver.get("http://localhost:6012/mfc-oa/web/app.php/ofertas/edicion/${oferta}")

                //ingreso compromiso participacion

                def ingresoCompromiso = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("btnFormularioCompromisoParticipacion"))
                )
                ingresoCompromiso.click()

                //EXPERIENCIA LABORAL

                String selectorCss = ".btnExpPro[idpersonal='${personal}']"
                WebElement ingresarExperiencia = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selectorCss)))
                ingresarExperiencia.click()

                def agregarExperienciaProfesional = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("agregarExperienciaProfesional"))
                )
                agregarExperienciaProfesional.click()

//
//                //tipo de documento
//                def comboUno = wait.until(
//                        ExpectedConditions.visibilityOfElementLocated(By.id("comproPartici_tipoDocumentoCp"))
//                )
//                Select seleccionarTipoDocumento = new Select(comboUno)
//                seleccionarTipoDocumento.selectByVisibleText("CÉDULA")
//
                //empresa

                def campoEmpresa = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("expProfe_empresaEp"))
                )
                campoEmpresa.clear()
                campoEmpresa.sendKeys("Tedein")
//
                //contratante

                def campoContratante = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("expProfe_contratanteEp"))
                )
                campoContratante.clear()
                campoContratante.sendKeys("Luis Lopez")
//
                //proyecto

                def campoProyecto = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("expProfe_proyectoEp"))
                )
                campoProyecto.clear()
                campoProyecto.sendKeys("XXXXXX")

                //monto

                def campoMonto = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("expProfe_montoProyecyoEp"))
                )
                campoMonto.clear()
                campoMonto.sendKeys("50000")

                //funcion

                def campoFuncion = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("expProfe_cargoEp"))
                )
                campoFuncion.clear()
                campoFuncion.sendKeys("Desarrollador web")

                //tiempo de participacion

                def campoTiempoParticipacion = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("expProfe_tiempoPartiConsCp"))
                )
                campoTiempoParticipacion.clear()
                campoTiempoParticipacion.sendKeys("10")

                //tiempo de participacion seleccion

                def comboTiempoParticipacion = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("expProfe_tiempoPartiConsCpMedida"))
                )
                Select seleccionarTiempoParticipacion= new Select(comboTiempoParticipacion)
                seleccionarTiempoParticipacion.selectByVisibleText("AÑOS")

                //actividades relevantes

                def comboNE = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(By.id("expProfe_activiRelevEp"))
                )
                Select seleccionarNivelEstudio = new Select(comboNE)
                seleccionarNivelEstudio.selectByVisibleText("Actividades.....")

                //boton guardar

                def guardarPersonal = wait.until(
                        ExpectedConditions.elementToBeClickable(By.id("guardarExperienciaProfesional")) )
                guardarPersonal.click()

                def guardarPersonal2 = wait.until(
                        ExpectedConditions.elementToBeClickable(By.id("guardarExperienciaProfesional")) )
                guardarPersonal2.click()

                WebElement botonGuardar = driver.findElement(By.id("guardarExperienciaProfesional"))

                JavascriptExecutor js3 = (JavascriptExecutor) driver
                js3.executeScript("arguments.focus();", botonGuardar)
                js3.executeScript("arguments.click();", botonGuardar)

            }    // Aquí puedes agregar clics, interactuar con formularios, etc.

        } catch (Exception e ) {
            println "Ocurrió un error en Selenium: ${e.message}"
            e.printStackTrace()
        } finally {
            // 5. Asegurar el cierre del proceso de Firefox
            if (driver != null) {
//                driver.quit()
                println "=== Terminado correctamente ==="
            }
        }
    }


    boolean verificarSiUrlExiste(String urlString) {
        try {
            URL url = new URL(urlString)
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection()
            conexion.requestMethod = "HEAD"
            conexion.connectTimeout = 5000  // 5 segundos de espera máxima
            conexion.readTimeout = 5000

            int codigoEstado = conexion.responseCode
            // Retorna verdadero si el estado HTTP es exitoso (2xx o 3xx)
            return (codigoEstado >= 200 && codigoEstado < 400)
        } catch (Exception e) {
            log.error("Error de conexión URL ${urlString}: ${e.message}")
            return false
        }
    }

}

