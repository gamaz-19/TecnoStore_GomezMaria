package Utilities;

import java.util.regex.Pattern;

public class Validator {

    // Patrón para validar correos electrónicos
    private static final Pattern EMAIL_PATTERN
            = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // Patrón para validar teléfonos (10 dígitos)
    private static final Pattern TELEFONO_PATTERN
            = Pattern.compile("^[0-9]{10}$");

    /* Valida que un correo electrónico tenga formato válido
     */
    public static boolean validarCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(correo).matches();
    }

    /* Valida que un teléfono tenga 10 dígitos
     */
    public static boolean validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return false;
        }
        return TELEFONO_PATTERN.matcher(telefono).matches();
    }

    /* Valida que un precio sea positivo
     */
    public static boolean validarPrecio(double precio) {
        return precio > 0;
    }

    /* Valida que el stock sea positivo o cero
     */
    public static boolean validarStock(int stock) {
        return stock >= 0;
    }

    /* Valida que una identificación no esté vacía y tenga entre 6 y 13 caracteres
     */
    public static boolean validarIdentificacion(String identificacion) {
        if (identificacion == null || identificacion.trim().isEmpty()) {
            return false;
        }
        int longitud = identificacion.trim().length();
        return longitud >= 6 && longitud <= 13;
    }

    /* Valida que un nombre no esté vacío
     */
    public static boolean validarNombre(String nombre) {
        return nombre != null && !nombre.trim().isEmpty() && nombre.trim().length() >= 3;
    }

    /* Valida que la cantidad sea positiva
     */
    public static boolean validarCantidad(int cantidad) {
        return cantidad > 0;
    }
}
