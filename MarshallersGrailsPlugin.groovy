import org.grails.plugins.marshallers.ExtendedConvertersConfigurationInitializer
import org.grails.plugins.marshallers.XmlMarshallerArtefactHandler
import org.grails.plugins.marshallers.JsonMarshallerArtefactHandler

class MarshallersGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.1 > *"
    
    def loadAfter = ['converters']
    
    
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def artefacts = [ XmlMarshallerArtefactHandler, JsonMarshallerArtefactHandler ]
    
    def author = "Predrag Knezevic"
    def authorEmail = "pedjak@gmail.com"
    def title = "Easy Custom XML and JSON Marshalling for Grails Converters"
    def description = '''\\
Brief description of the plugin.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/marshallers"

    def doWithSpring = {
        // replace default convertersConfigurationInitializer from Converters plugin with ours
        convertersConfigurationInitializer(ExtendedConvertersConfigurationInitializer)
        ["xml", "json"].each { type ->
            application."${type}MarshallerClasses".each { marshallerClass ->
                "${marshallerClass.fullName}"(marshallerClass.clazz) { bean ->
                    bean.autowire = "byName"
                }
            }
        }
    }
        
}