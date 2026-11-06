# Creepy Toys - App E-Commerce para dispositivos Android
Esta es una aplicación nativa para sistema Android enfocada en la venta de juguetes coleccionables de terror con franquicias como Chucky, Five Nights At Freddy´s (FNAF), Poppy Playtime y Stranger things.
Desarrolé esta aplicación para resolver el flujo completo de una tienda en línea (E2E) con una interfaz adaptable a diferentes pantallas, filtrado dinámico de productos, persistencia de datos local y un checkout funcional con validaciones de pago.

# Propósito del proyecto:
Esta aplicación sirve como un entorno de pruebas para Quality Assurance (QA). El objetivo es tener un marco controlado para la ejecución de pruebas manuales y la automatización de casos de prueba de Android.

# Cobertura y casos de prueba clave:
**UI y maquetación responsiva**:
- Configuración de grilla responsiva (GridLayoutManager): 2 columnas en modo vertical (portrait) y 3 columnas en horizontal (landscape).
- Adaptabilidad de tarjetas de producto con  ConstraintLayout evitando compresión o truncamiento de texto.
- visualización flotante de imágenes a pantalla completa con AlertDialog al interactuar con las tarjetas.
**Lógica de negocio y carrito**:
- Persistencia local con SharedPreferences (CartPreferences).
- Sincronización en tiempo real del badge contador en la ToolBar mediante el ciclo de vida (onResume).
- Gestión dinámica de Items (sumar, restar y vaciar el carrito) dentro de un model BottomSheetDialog.
**Flujo de Checkout y validaciones**:
- Transferencia de datos y validaciones vía Intent extras.
- Validación de campos obligatorios en el formulario de envío (Nombre y Dirección).
- Visibilidad condicional según el método de pago elegido (mostrar/ocultar campos de tarjeta al cambiar entre efectivo y tarjeta).
- Borrado automático de la sección del carrito al terminar el pedido.
    
## Funcionalidades
**Interfaz adaptable a diferentes tipos de pantallas** (smartphones, tablets) 
**Identidad visual temática**: Toolbar en color `80D0000` con un ícono de silueta custom y componenetes material design.
**Filtros por categoría**: Filtrado en tiempo real mediante `ChipGroup` para buscar productos por franquicia.
**Carrito de compras persistente**:
- Modal dinámico usando `BottomSheetDialog`.
- Contador de artículos en la Toolbar en tiempo real con `BadgeDrawable`.
- Persistencia de datos local mediante `SharedPreferences` para conservar el carrito aunque se cierre la app.
  **Checkout y métodos de pago**:
- Formulario con validación de campos obligatorios para la entrega.
- Opciones de pago con tarjeta (Crédito/Débito) o Efectivo contra-entrega.
- Ocultamiento/Muestre dinámica de campos de tarjeta según el método seleccionado.
- Limpieza automática del carrito tras confirmar la orden de compra.

  ## Tecnologías usadas:
  **Lenguaje**: Kotlin.
  **Diseño y UI**: XML, Material components (`MaterialToolbar`, `ChipGroup`, `TextInputLayout`, `BottomSheetDialog`).
  **Almacenamiento Local**: `SharedPreferences`.
  **Navegación**: Intents y paso de datos entre actividades.


  ## Estructura del código:

com.example.creepytoys/
├── data/
│   └── CartPreferences.kt         # Persistencia local / SharedPreferences
├── domain/
│   ├── model/
│   │   └── ToyItem.kt             # Modelo de datos para los juguetes del catálogo
│   ├── repository/                # Interfaces y contratos del repositorio
│   └── usecase/                   # Casos de uso de la aplicación
└── ui/
    ├── theme/                     # Configuración de estilos y temas de la UI
    ├── CartAdapter.kt             # Adapter de RecyclerView para el carrito
    ├── CatalogActivity.kt          # Pantalla principal del catálogo
    ├── CheckoutActivity.kt        # Pantalla de checkout
    ├── ToyAdapter.kt              # Adapter de RecyclerView para los juguetes
    └── MainActivity.kt            # Actividad principal / de inicio

## Requisitos previos:
Esto es lo que necesitas para ejecutar el proyecto:
- **Android Studio**: Jellyfish 2023 o superior.
- **JDK**: Versión 17 o superior.
- **SDK de Android**: API 24 (Android 7.0 Nougat) como mínimo, compilado en API 34 o superior.
- **Dispositivo de prueba**: Emulador de Android Studio o un dispositivo físico con la depuración inalámbrica activa.

## Instalación y ejecución:
- **Clona el repositorio**:
- git clone [https://github.com/tu-usuario/CreepyToys.git](https://github.com/tu-usuario/CreepyToys.git)
- Abre el proyecto en Android Studio.
- Selecciona Open file y busca la carpeta donde clonaste el repositorio.
- Sincroniza las dependencias con Gradle
- Deja que Android Studio descargue y sincronice los archivos de Gradle automáticamente.
- Si no se ejecuta automáticamente, presiona Sync Project with Gradle Files (es un elefante en la parte superior).
- Para ejecutar la app puedes conectar un dispositivo físico por USB o de forma inalámbrica o puedes iniciar un emulador en Device Manager.
- Presiona el botón play.
