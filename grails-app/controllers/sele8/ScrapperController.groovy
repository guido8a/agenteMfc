package sele8

class ScraperController {

    // Inyección automática de Grails por nombre
    ScraperService scraperService

    def index() {
        String url = params.url ?: "https://google.com"

        // Ejecuta el flujo en la máquina remota
        String tituloPagina = scraperService.ejecutarProcesoRemoto(url)

        render "El proceso remoto en Firefox abrió la página y el título es: ${tituloPagina}"
    }
}
