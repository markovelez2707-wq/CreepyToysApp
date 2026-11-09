## Plan de pruebas (TEST PLAN) - Creepy Toys App:

## Introducción y objetivo:
El objetivo de este plan de pruebas es validar la calidad, usabilidad, responsividad y correcto funcionamiento del flujo completo de compra (E2E) en la aplicación.

## Alcance (Scope):
## Dentro del alcance (In Scope):
**UI y layouts**: Verificación de responsividad en 2 columnas (portrait) y en API 3 columnas (landscape) con `GridLayoutManager`.
**Visualización**: Renderizado de tarjetas sin truncamiento de texto y modal de imagen flotante.
**Filtros**: Filtrado por franquicias (*Chucky*, *FNAF*, *Poppy Playtime*, *Stranger Things*).
**Persistencia local**: Operaciones CRUD en el carrito usando `SharedPreferences` (`CartPreferences`).
**Sincronización del UI**: Badge en la ToolBar actualizado en tiempo real y durante el ciclo de vida (`onResume`).
**Checkout y validaciones**: Validaciones de formulario (envío y tarjeta) y ocultamiento dinámico según el método de pago seleccionado.
**Reseteo**: Limpieza completa de datos tras la confirmación del pedido.

## Fuera del alcance (Out of Scope):
- Procesamiento de pagos reales con pasarelas (Stripe, Pay Pal, etc.).
- Autenticación de usuario o login remoto.
- Integración con APIs REST / Backend en la nube.

## Entorno de pruebas:
- **Dispositivos**: Emulador Android Studio AVD Google Pixel 9 / Dispositivo Físico Android.
- **Versión Mínima SDK**: Android 9.0 Pie (API 28).
- **Orientaciones**: Vertical (Portrait) y horizontal (Landscape).

## Estrategia de pruebas:
Ejecutamos una combinación de:
- **Pruebas de funcionalidad**: Validación de comportamiento de la interfaz y persistencia de datos.
- **Pruebas de UI/UX y Responsividad**: Adaptación de cuadrículas y legibilidad de elementos visuales.
- **Pruebas de caso negativo / Validaciones**: Control de errores ante entradas vacías o incompletas en el formulario del Checkout.
- **Pruebas End to End (E2E)**: Simulación del flujo completo de un usuario desde la exploración del catálogo hasta la confirmación de la compra.

# Matriz de Casos de Prueba - Creepy Toys App

En este documento registro la ejecución de los casos de prueba para el flujo de la aplicación en tablet.

* **Dispositivo / Entorno:** Tablet Android (Emulador Pixel Tablet)
* **Sistema Operativo:** Android 9.0 Pie (API 28)
* **Fecha de ejecución:** Noviembre 2026

---

# 📋 Matriz de Casos de Prueba (Test Cases)

**Proyecto:** Creepy Toys App  
**Entorno de ejecución:** Tablet (Emulador / Dispositivo Físico Android — API 28+)

---

## 📊 Resumen de Casos de Prueba

| ID Caso | Módulo | Descripción / Escenario | Pasos de Ejecución | Resultado Esperado | Estado | Evidencia |
| :--- | :--- | :--- | :--- | :--- | :---: | :---: |
| **TC-001** | UI | Responsividad Tablet (Portrait) | Abrir la app en posición vertical en la tablet. | El catálogo se muestra distribuido en **2 columnas** aprovehando el ancho de pantalla. | PASSED | [Ver Imagen](#tc-001--tc-002-responsividad-en-tablet) |
| **TC-002** | UI | Responsividad Tablet (Landscape) | Girar la tablet a posición horizontal. | El catálogo se reajusta a **3 columnas** sin superposición de elementos. | PASSED | [Ver Imagen](#tc-001--tc-002-responsividad-en-tablet) |
| **TC-003** | UI | Modal de Imagen | Hacer tap sobre la imagen de cualquier juguete. | Se despliega un diálogo flotante a pantalla completa con la imagen del producto. | PASSED | [Ver Imagen](#tc-003--tc-004-interacción-y-filtros) |
| **TC-004** | Filtros | Filtrado por Franquicia | Seleccionar el Chip "Chucky". | La lista se filtra mostrando únicamente productos de la franquicia seleccionada. | PASSED | [Ver Imagen](#tc-003--tc-004-interacción-y-filtros) |
| **TC-005** | Carrito | Agregar producto y Badge | Presionar "Agregar" en un producto. | El botón notifica con un Toast y el Badge de la Toolbar incrementa su valor. | PASSED | [Ver Imagen](#tc-005--tc-007-gestión-de-carrito) |
| **TC-006** | Carrito | Persistencia local | Agregar artículos, cerrar/destruir la app y reabrir. | El carrito y el Badge mantienen los artículos previamente agregados. | PASSED | [Ver Imagen](#tc-005--tc-007-gestión-de-carrito) |
| **TC-007** | Carrito | Modificar cantidades | Abrir el BottomSheet/Diálogo del carrito y presionar `+` / `-`. | El total acumulado y las cantidades unitarias se recalculan en tiempo real. | PASSED | [Ver Imagen](#tc-005--tc-007-gestión-de-carrito) |
| **TC-008** | Checkout | Validación de Campos Vacíos | Presionar "Confirmar Pedido" con nombre (< 10 chars) o dirección (< 10 chars). | Muestra mensaje de error en el campo correspondiente (`error`) y solicita corregir los datos. | PASSED | [Ver Imagen](#tc-008--tc-012-validaciones-de-checkout-y-ux) |
| **TC-009** | Checkout | Lógica de Método de Pago | Seleccionar la opción "Pago en Efectivo". | El contenedor con los campos de la tarjeta (`container_card_fields`) se oculta (`GONE`). | PASSED | [Ver Imagen](#tc-008--tc-012-validaciones-de-checkout-y-ux) |
| **TC-010** | Checkout | Validación de Tarjeta | Seleccionar "Tarjeta" e ingresar < 16 dígitos, fecha inválida o CVV < 3 dígitos. | Arroja mensaje de error impidiendo procesar el pago hasta corregirlo. | PASSED | [Ver Imagen](#tc-008--tc-012-validaciones-de-checkout-y-ux) |
| **TC-011** | UX | Auto-formato de Fecha MM/AA | Escribir la secuencia de dígitos `0` `5` `3` `0` en el campo de vencimiento. | El campo autocompleta la diagonal mostrando `05/30` sin intervención del usuario. | PASSED | [Ver Imagen](#tc-008--tc-012-validaciones-de-checkout-y-ux) |
| **TC-012** | UX | Borrado fluido de Fecha | Presionar *Backspace* teniendo ingresado el valor `05/30`. | La diagonal se borra limpiamente sin bloquear la edición ni duplicar caracteres. | PASSED | [Ver Imagen](#tc-008--tc-012-validaciones-de-checkout-y-ux) |
| **TC-013** | E2E | Compra Exitosa con Nombre | Completar datos con Nombre `"Carlos Ruiz"` y confirmar pedido. | Muestra `AlertDialog` con mensaje personalizado *"¡Gracias por tu compra, Carlos Ruiz!..."*, limpia `SharedPreferences` y resetea el carrito a `0`. | PASSED | [Ver Imagen](#tc-013-flujo-end-to-end-exitoso) |

---

## 📷 Evidencias de Ejecución (Screenshots)



### TC-001 & TC-002: Responsividad en Tablet
| Modo Portrait (Tablet Vertical) | Modo Landscape (Tablet Horizontal) |
| :---: | :---: |
| ![TC-001 Portrait Tablet](screenshots/tc001_tablet_portrait.png) | ![TC-002 Landscape Tablet](screenshots/tc002_tablet_landscape.png) |

### TC-003 & TC-004: Interacción y Filtros
| Modal de Imagen Ampliada | Filtro Dinámico por Franquicia |
| :---: | :---: |
| ![TC-003 Modal](screenshots/tc003_image_modal.png) | ![TC-004 Filtro](screenshots/tc004_filter.png) |

### TC-005 a TC-007: Gestión de Carrito
| Badge en Toolbar | Carrito Persistente (BottomSheet/Diálogo) |
| :---: | :---: |
| ![TC-005 Badge](screenshots/tc005_badge.png) | ![TC-007 BottomSheet](screenshots/tc007_cart_dialog.png) |

### TC-008 a TC-012: Validaciones de Checkout y UX
| Formato Automático MM/AA | Ocultamiento de Campos (Efectivo) | Validación de Datos |
| :---: | :---: | :---: |
| ![TC-011 Auto-formato](screenshots/tc011_auto_format.png) | ![TC-009 Efectivo](screenshots/tc009_cash_payment.png) | ![TC-010 Validación](screenshots/tc010_validation_error.png) |

### TC-013: Flujo End-to-End Exitoso
| Diálogo Personalizado con Nombre del Usuario y Reseteo |
| :---: |
| ![TC-013 Éxito Personalizado](screenshots/tc013_success_personalized.png) |
