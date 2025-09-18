# Azure Functions HTTP Trigger - Flex Consumption

Este proyecto contiene una Azure Function HTTP Trigger desarrollada en Java 17 para despliegue en **Flex Consumption**.

## Descripción

La función responde con el mensaje "hola soy un flex" cuando se invoca a través de HTTP GET o POST.

## Estructura del Proyecto

```
azure-flex-function/
├── src/main/java/com/example/Function.java  # Función HTTP Trigger
├── pom.xml                                  # Configuración Maven
├── host.json                               # Configuración Azure Functions
├── local.settings.json                     # Configuración local
└── target/
    ├── azure-flex-function-1.0.0.jar      # JAR principal
    └── azure-functions/                    # Artefactos para despliegue
        └── azure-flex-function/
            ├── azure-flex-function-1.0.0.jar
            ├── host.json
            ├── local.settings.json
            └── HttpTriggerFlex/
                └── function.json
```

## Requisitos

- Java 17
- Maven 3.6+
- Azure CLI
- Azure Functions Core Tools v4

## Compilación

```bash
mvn clean package
```

Esto generará:
- `target/azure-flex-function-1.0.0.jar` - JAR principal
- `target/azure-functions/azure-flex-function/` - Directorio listo para despliegue

## Ejecución Local

```bash
# Instalar Azure Functions Core Tools si no está instalado
npm install -g azure-functions-core-tools@4 --unsafe-perm true

# Ejecutar localmente
func start
```

La función estará disponible en: `http://localhost:7071/api/HttpTriggerFlex`

## Despliegue en Azure Flex Consumption

### 1. Crear Resource Group
```bash
az group create --name rg-azure-flex-function --location "East US"
```

### 2. Crear Storage Account
```bash
az storage account create \
  --name storageflexfunction \
  --resource-group rg-azure-flex-function \
  --location "East US" \
  --sku Standard_LRS
```

### 3. Crear Function App con Flex Consumption
```bash
az functionapp create \
  --resource-group rg-azure-flex-function \
  --name azure-flex-function \
  --storage-account storageflexfunction \
  --runtime java \
  --runtime-version 17 \
  --functions-version 4 \
  --consumption-plan-location "East US" \
  --sku FlexConsumption
```

### 4. Desplegar usando Maven
```bash
mvn azure-functions:deploy
```

### 5. Desplegar usando Azure CLI
```bash
az functionapp deployment source config-zip \
  --resource-group rg-azure-flex-function \
  --name azure-flex-function \
  --src target/azure-functions/azure-flex-function.zip
```

## Prueba de la Función

Una vez desplegada, puedes probar la función:

```bash
# GET request
curl https://azure-flex-function.azurewebsites.net/api/HttpTriggerFlex

# POST request
curl -X POST https://azure-flex-function.azurewebsites.net/api/HttpTriggerFlex
```

**Respuesta esperada:** `hola soy un flex`

## Características de Flex Consumption

- **Escalado automático**: Escala desde 0 hasta miles de instancias
- **Facturación por uso**: Solo pagas por el tiempo de ejecución
- **Arranque rápido**: Menor tiempo de arranque en frío
- **Optimizado para Java**: Mejor rendimiento para aplicaciones Java

## Configuración Adicional

### Variables de Entorno
Puedes agregar variables de entorno en el portal de Azure o usando Azure CLI:

```bash
az functionapp config appsettings set \
  --resource-group rg-azure-flex-function \
  --name azure-flex-function \
  --settings "CUSTOM_SETTING=value"
```

### Monitoreo
La función incluye logging automático. Puedes ver los logs en:
- Portal de Azure > Function App > Monitor
- Application Insights (si está configurado)

## Troubleshooting

1. **Error de compilación**: Verificar que Java 17 esté instalado
2. **Error de despliegue**: Verificar que el nombre de la Function App sea único
3. **Función no responde**: Verificar los logs en el portal de Azure

## Limpieza de Recursos

```bash
az group delete --name rg-azure-flex-function --yes --no-wait
```