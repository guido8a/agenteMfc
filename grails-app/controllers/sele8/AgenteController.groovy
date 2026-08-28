package sele8


class AgenteController {

    // Grails inyecta automáticamente tu servicio de automatización aquí
    SeleniumAutomatorService seleniumAutomatorService

    // Endpoint: http://localhost:8080/selenium/index
    def index() {
        // Retorna una vista simple con un botón para iniciar
        render """
        <html>
        <head>
            <title>Panel Selenium</title>
            <style>
                body { font-family: Arial, sans-serif; margin: 40px; text-align: center; }
                .btn { padding: 15px 30px; font-size: 18px; color: white; background-color: #007bff; border: none; border-radius: 5px; cursor: pointer; }
                .btn:hover { background-color: #0056b3; }
            </style>
        </head>
        <body>
            <h2>Controlador de Automatización Selenium (Firefox)</h2>
            <p>Haz clic abajo para iniciar el proceso en el servidor</p>
            <form action="/agente/iniciar" method="POST">
                <button type="submit" class="btn">Arrancar Selenium</button>
            </form>
        </body>
        </html>
        """
    }

    // Endpoint: http://localhost:8080/selenium/iniciar
    def iniciar() {
        println "=== Petición web recibida: Iniciando Selenium ==="

        try {
            // Se ejecuta la automatización en segundo plano o ventana visible según tu servicio
            seleniumAutomatorService.ejecutarAutomatizacion()

            // Respondemos al navegador cuando termine con éxito
            render status: 200, text: "Automatización completada con éxito. Revisa la consola de IntelliJ."
        } catch (Exception e) {
            log.error("Error al ejecutar Selenium desde la web", e)
            render status: 500, text: "Error en la automatización: ${e.message}"
        }
    }
}
