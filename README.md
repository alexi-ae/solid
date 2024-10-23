# Principios SOLID

Los principios SOLID son un conjunto de cinco principios de diseño de software que ayudan a los desarrolladores a crear sistemas más mantenibles, flexibles y escalables. Estos principios fueron promovidos por Robert C. Martin (Uncle Bob) y son fundamentales en la programación orientada a objetos (OOP).

## 1. Principio de Responsabilidad Única (SRP)
Cada clase debe tener una única responsabilidad o razón para cambiar. En otras palabras, una clase debe estar enfocada en cumplir con una tarea o propósito específico.

### Beneficios del SRP:
- **Facilidad de mantenimiento**: Al tener una responsabilidad clara, cualquier cambio en la funcionalidad afecta solo a una clase.
- **Código más claro**: El código es más legible y comprensible.
- **Reutilización**: Las clases con responsabilidades bien definidas son más fáciles de reutilizar.

## 2. Principio Abierto/Cerrado (OCP)
Las clases deben estar abiertas para la extensión, pero cerradas para la modificación. Esto significa que el comportamiento de una clase debe poder extenderse sin modificar su código fuente.

### Beneficios del OCP:
- **Reducción de errores**: No es necesario modificar clases existentes, lo que reduce la posibilidad de introducir errores.
- **Flexibilidad**: Se pueden añadir nuevas funcionalidades sin afectar las clases ya probadas.

## 3. Principio de Sustitución de Liskov (LSP)
Las clases derivadas deben ser sustituibles por sus clases base sin alterar el comportamiento correcto del programa. En otras palabras, cualquier instancia de una clase hija debe poder reemplazar a una instancia de la clase padre sin problemas.

### Beneficios del LSP:
- **Intercambiabilidad**: Se pueden utilizar clases hijas sin modificar el código cliente.
- **Mejoras en la reutilización**: Se puede confiar en que el comportamiento de las clases hijas es consistente con el de las clases base.

## 4. Principio de Segregación de Interfaces (ISP)
Los clientes no deben verse obligados a depender de interfaces que no usan. En vez de tener interfaces "grandes", es preferible crear varias interfaces más pequeñas y específicas.

### Beneficios del ISP:
- **Interfaces más simples**: Se evita que los clientes implementen métodos que no necesitan.
- **Reducción de acoplamiento**: Menos dependencia de interfaces grandes y generales, lo que facilita el mantenimiento.

## 5. Principio de Inversión de Dependencias (DIP)
Las clases de alto nivel no deben depender de clases de bajo nivel. Ambas deben depender de abstracciones (interfaces o clases abstractas). Además, las abstracciones no deben depender de los detalles, sino que los detalles deben depender de las abstracciones.

### Beneficios del DIP:
- **Desacoplamiento**: Las clases están desacopladas de sus implementaciones concretas, lo que facilita los cambios y el mantenimiento.
- **Facilidad de pruebas**: El uso de interfaces permite utilizar mocks o stubs en las pruebas unitarias.
- **Flexibilidad**: Las implementaciones pueden ser reemplazadas fácilmente sin cambiar el código cliente.

---

## Principio de Responsabilidad Única (SRP)

El **Principio de Responsabilidad Única (SRP)** establece que una clase debe tener una y solo una
razón para cambiar, es decir, debe ser responsable de una sola funcionalidad o tarea dentro del
sistema.

### Ejemplo que no cumple con el SRP:

El siguiente código muestra un ejemplo de una clase de servicio que no cumple con el **Principio de
Responsabilidad Única (SRP)**. En este caso, la clase `TransactionNotSrpService` es responsable de
varias tareas que deberían estar separadas en diferentes clases:

```java

@Service
public class TransactionNotSrpService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private AccountRepository accountRepository;

  public void createTransaction(Long accountId, BigDecimal amount) {
    // 1. Validación dentro del servicio
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("El monto debe ser mayor a 0");
    }

    // 2. Crear transacción
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
    Transaction transaction = new Transaction();
    transaction.setAccount(account);
    transaction.setAmount(amount);
    transaction.setStatus("CREATED");
    transaction = transactionRepository.save(transaction);

    // 3. Enviar notificación dentro del servicio
    sendNotification(transaction);
  }

  // Método para enviar notificaciones
  private void sendNotification(Transaction transaction) {
    System.out.println("Notificación enviada para la transacción: " + transaction.getId());
  }
}
```

### Problemas en la implementación

En este caso, la clase `TransactionNotSrpService` tiene más de una responsabilidad:

- **Validación de la transacción**: La lógica para validar que el monto es válido (mayor que 0) está
  dentro del mismo servicio. Esto debería estar separado en una clase de validación dedicada.
- **Creación y persistencia de la transacción**: La creación de la transacción y la interacción con
  el repositorio debería ser la única responsabilidad de este servicio.
- **Envío de notificaciones**: El envío de notificaciones (como un correo electrónico o mensaje) no
  es responsabilidad del servicio de transacciones, y debería estar en un servicio separado.

### Consecuencias de no seguir el SRP

- **Difícil de mantener**: Si las reglas de validación o la lógica de notificación cambian, tendrás
  que modificar esta clase de servicio, lo que aumenta el riesgo de introducir errores.
- **Difícil de probar**: Dado que la clase realiza múltiples tareas, las pruebas unitarias serán más
  complicadas, ya que necesitarás probar la validación, la creación de la transacción y el envío de
  notificaciones en un solo test.
- **Acoplamiento**: La clase está altamente acoplada a diferentes responsabilidades, lo que viola el
  principio de alta cohesión y bajo acoplamiento.

### Solución para cumplir con el SRP

Para cumplir con el principio de responsabilidad única, se deben separar las responsabilidades en
diferentes clases:

- **Validador de transacciones**: Una clase dedicada a la validación de la transacción.
- **Servicio de notificaciones**: Una clase que maneje el envío de notificaciones.
- **Servicio de transacciones**: Solo debe encargarse de la lógica relacionada con la creación y
  persistencia de la transacción.

```java

@Service
public class TransactionSrpService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TransactionValidationService validationService;

  @Autowired
  private NotificationService notificationService;

  public void createTransaction(Long accountId, BigDecimal amount) {
    // 1. Validar la transacción usando un servicio de validación
    validationService.validateTransaction(amount);

    // 2. Crear la transacción
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
    Transaction transaction = new Transaction();
    transaction.setAccount(account);
    transaction.setAmount(amount);
    transaction.setStatus("CREATED");
    transaction = transactionRepository.save(transaction);

    // 3. Enviar notificación usando un servicio de notificación
    notificationService.sendTransactionNotification(transaction);
  }

}

@Service
public class NotificationService {

  public void sendTransactionNotification(Transaction transaction) {
    // Lógica para enviar una notificación
    System.out.println("Notificación enviada para la transacción: " + transaction.getId());
  }
}

@Service
public class TransactionValidationService {

  public void validateTransaction(BigDecimal amount) {
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("El monto debe ser mayor a 0");
    }
  }
}
```

---

## Principio Abierto/Cerrado (OCP)

El **Principio Abierto/Cerrado** (OCP, por sus siglas en inglés) es uno de los principios SOLID que
establece que las "entidades de software (clases, módulos, funciones, etc.) deben estar **abiertas
para la extensión, pero cerradas para la modificación**". Esto significa que se debe poder agregar
nueva funcionalidad sin modificar el código existente.

### Ejemplo que no cumple con el OCP:

```java

@Service
public class TransactionNotOcpService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Transactional
  public void processTransaction(String transactionType, Long accountId, BigDecimal amount) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    switch (transactionType) {
      case "deposit":
        BigDecimal newBalanceDeposit = account.getBalance().add(amount);
        account.setBalance(newBalanceDeposit);
        Transaction txDeposit = new Transaction();
        txDeposit.setAccount(account);
        txDeposit.setAmount(amount);
        txDeposit.setType(transactionType);
        transactionRepository.save(txDeposit);
        break;
      case "withdraw":
        if (account.getBalance().compareTo(amount) >= 0) {
          BigDecimal newBalanceWithdraw = account.getBalance().subtract(amount);
          account.setBalance(newBalanceWithdraw);
          Transaction txWithdraw = new Transaction();
          txWithdraw.setAccount(account);
          txWithdraw.setAmount(amount);
          txWithdraw.setType(transactionType);
          transactionRepository.save(txWithdraw);
        } else {
          throw new IllegalArgumentException("Insufficient balance");
        }
        break;
      case "transfer":
        // Supongamos que tenemos otra cuenta y queremos transferir a esa cuenta.
        // Este código es solo un ejemplo. Aquí deberías implementar la lógica de transferencia.
        throw new UnsupportedOperationException("Transfer operation is not implemented yet.");
      default:
        throw new UnsupportedOperationException("Transaction type not supported");
    }

    accountRepository.save(account); // Persist the account after transaction
  }
}
```

### Problemas en la implementación

En este caso, la clase `TransactionNotOcpService` viola el principio de **Open/Closed (OCP)**, ya
que está:

- **Abierta a modificaciones**: Cada vez que se agregue un nuevo tipo de transacción (por ejemplo,
  una "transferencia" o cualquier otro tipo futuro), el código de esta clase deberá modificarse.
- **No extensible**: No es posible extender el comportamiento para nuevos tipos de transacciones sin
  cambiar directamente la clase.

### ¿Por qué no cumple con el principio OCP?

El principio OCP establece que las clases deben estar **abiertas para extensión** pero **cerradas
para modificación**. En este caso, cada vez que se desea agregar un nuevo tipo de transacción, se
debe modificar el método `processTransaction` agregando más lógica al `switch`, lo que viola el OCP.

#### Consecuencias de no seguir el OCP

- **Alta fragilidad**: Cada vez que se modifique el método, existe el riesgo de introducir errores
  en la lógica existente.
- **Dificultad para agregar nuevas funcionalidades**: La adición de nuevas transacciones obliga a
  modificar el código base en lugar de simplemente agregar nuevas clases que extiendan el
  comportamiento.
- **Pruebas más complejas**: A medida que crece la cantidad de tipos de transacciones, el `switch`
  se hace más difícil de mantener y probar.

### Solución para cumplir con el OCP

Para cumplir con el principio de **Open/Closed**, se puede aplicar el patrón de **Polimorfismo**
y **Clases Abstracciones**. Cada tipo de transacción debe ser representado por una clase que
implemente una interfaz o extienda una clase base, de modo que el comportamiento pueda ser extendido
sin modificar la clase `TransactionService`.

1. **Interfaz o clase abstracta** para transacciones: Crear una interfaz `TransactionProcessor` con
   un método `processTransaction`.
2. **Clases específicas para cada transacción**: Crear clases que implementen esta interfaz,
   como `DepositTransactionProcessor`, `WithdrawTransactionProcessor`, etc.
3. **Factory** para decidir qué transacción ejecutar en tiempo de ejecución.

```java
public interface TransactionOcp {

  void execute(Account account);
}

public class WithdrawTransactionOcp implements TransactionOcp {

  private BigDecimal amount;

  public WithdrawTransactionOcp(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public void execute(Account account) {
    BigDecimal currentBalance = account.getBalance();
    if (currentBalance.compareTo(amount) >= 0) {
      BigDecimal newBalance = currentBalance.subtract(amount);
      account.setBalance(newBalance);
    } else {
      throw new IllegalArgumentException("Insufficient balance");
    }
  }
}

public class DepositTransactionOcp implements TransactionOcp {

  private BigDecimal amount;

  public DepositTransactionOcp(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public void execute(Account account) {
    BigDecimal newBalance = account.getBalance().add(amount);
    account.setBalance(newBalance);
  }
}

public class TransactionOcpFactory {

  public static TransactionOcp createTransaction(String transactionType, BigDecimal amount) {
    switch (transactionType.toLowerCase()) {
      case "deposit":
        return new DepositTransactionOcp(amount);
      case "withdraw":
        return new WithdrawTransactionOcp(amount);
      default:
        throw new IllegalArgumentException("Unsupported transaction type: " + transactionType);
    }
  }
}

@Service
public class TransactionOcpService {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Transactional
  public void processTransaction(String transactionType, Long accountId, BigDecimal amount) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    // Utilizar la fábrica para obtener la transacción adecuada
    TransactionOcp transaction = TransactionOcpFactory.createTransaction(transactionType, amount);

    transaction.execute(account);

    // Guardar la transacción en la base de datos
    Transaction trx = new Transaction();
    trx.setAccount(account);
    trx.setAmount(amount);
    trx.setType(transactionType);
    transactionRepository.save(trx);

    // Guardar la cuenta actualizada después de la transacción
    accountRepository.save(account);
  }
}
```

### Beneficios de seguir el OCP

- **Extensibilidad**: Se puede agregar cualquier tipo de transacción en el futuro sin modificar el
  código existente.
- **Mantenibilidad**: El código es más fácil de mantener porque está modularizado en clases que
  representan cada tipo de transacción.
- **Pruebas más sencillas**: Las pruebas unitarias para cada tipo de transacción pueden realizarse
  de manera independiente.

---

## Principio de Sustitución de Liskov (LSP)

El **Principio de Sustitución de Liskov** establece que "los objetos de una clase derivada deben
poder sustituirse por objetos de la clase base sin alterar las propiedades del programa". En otras
palabras, si `S` es una subclase de `T`, entonces los objetos de tipo `T` deben poder ser
reemplazados por objetos de tipo `S` sin afectar el funcionamiento del programa.

### Concepto

El LSP promueve la idea de que las subclases deben comportarse de manera coherente con su clase
base. Esto permite que se utilicen clases derivadas de manera intercambiable con sus clases base,
mejorando la modularidad y la reutilización del código.

### Ejemplo que no cumple con el LSP:

```java
public interface TransactionDoesNotLsp {

  void execute(Account account);
}

public class WithdrawTransactionDoesNotLsp implements TransactionDoesNotLsp {

  private BigDecimal amount;

  public WithdrawTransactionDoesNotLsp(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public void execute(Account account) {
    BigDecimal currentBalance = account.getBalance();
    if (currentBalance.compareTo(amount) >= 0) {
      BigDecimal newBalance = currentBalance.subtract(amount);
      account.setBalance(newBalance);
    } else {
      throw new IllegalArgumentException("Insufficient balance");
    }
  }
}

public class DepositTransactionDoesNotLsp implements TransactionDoesNotLsp {

  private BigDecimal amount;

  public DepositTransactionDoesNotLsp(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public void execute(Account account) {
    BigDecimal newBalance = account.getBalance().add(amount);
    account.setBalance(newBalance);
  }
}

public class TransferTransactionNotLsp implements TransactionDoesNotLsp {

  private BigDecimal amount;

  private Account accountTarget;

  public TransferTransactionNotLsp(BigDecimal amount, Account accountTarget) {
    this.amount = amount;
    this.accountTarget = accountTarget;
  }

  @Override
  public void execute(Account account) {
    BigDecimal currentBalanceOrigin = account.getBalance();
    // Valida si el saldo es suficiente de la cuenta de origen
    if (account.getBalance().compareTo(amount) < 0) {
      throw new IllegalArgumentException("Insufficient funds");
    }

    // Actualiza el saldo de la cuenta de origen
    BigDecimal newBalanceOrigin = currentBalanceOrigin.subtract(amount);
    account.setBalance(newBalanceOrigin);

    // Actualiza el saldo de la cuenta destino
    BigDecimal currentBalanceDestiny = accountTarget.getBalance();
    BigDecimal newBalanceDestiny = currentBalanceDestiny.add(amount);
    accountTarget.setBalance(newBalanceDestiny);
  }
}

public class TransactionDoesNotLspFactory {

  // el factory no utiliza todos los parametros para los servicios especificos
  // solo se necesita la cuenta destino cuando el tipo de trx es transfer
  public static TransactionDoesNotLsp createTransaction(String transactionType, BigDecimal amount,
      Account account) {
    switch (transactionType.toLowerCase()) {
      case "deposit":
        return new DepositTransactionDoesNotLsp(amount);
      case "withdraw":
        return new WithdrawTransactionDoesNotLsp(amount);
      case "transfer":
        return new TransferTransactionNotLsp(amount, account);
      default:
        throw new IllegalArgumentException("Unsupported transaction type: " + transactionType);
    }
  }
}

@Service
public class TransactionDoesNotLspService {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Transactional
  public void processTransaction(String transactionType, Long accountIdOrigin,
      Long accountIdDestiny, BigDecimal amount) {
    Account account = accountRepository.findById(accountIdOrigin)
        .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    // obtiene cuenta destino si es de tipo transfer
    Account accountDestiny = null;
    if ("transfer".equalsIgnoreCase(transactionType)) {
      accountDestiny = accountRepository.findById(accountIdOrigin)
          .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    }

    // Utilizar la fábrica para obtener la transacción adecuada
    // para este caso necesita la cuenta destino, aunque no todos lo usen
    TransactionDoesNotLsp transaction = TransactionDoesNotLspFactory
        .createTransaction(transactionType, amount, accountDestiny);

    transaction.execute(account);

    // Guardar la transacción en la base de datos - trx origen
    Transaction trxOrigen = new Transaction();
    trxOrigen.setAccount(account);
    trxOrigen.setAmount(amount.negate());
    trxOrigen.setType(transactionType);
    transactionRepository.save(trxOrigen);

    // Guardar la transacción en la base de datos - trx destino
    Transaction trxDestiny = new Transaction();
    trxDestiny.setAccount(account);
    trxDestiny.setAmount(amount);
    trxDestiny.setType(transactionType);
    transactionRepository.save(trxDestiny);

    // Guardar la cuenta actualizada después de la transacción
    accountRepository.save(account);
    accountRepository.save(accountDestiny);
  }
}

```

### Problemas en la implementación

En este caso, el diseño no cumple con el LSP porque:

- **Dependencia innecesaria**: La fábrica `TransactionDoesNotLspFactory` requiere la cuenta destino
  para todos los tipos de transacción, aunque solo es necesaria para transferencias. Esto introduce
  una dependencia innecesaria en las otras transacciones (`deposit` y `withdraw`), que no utilizan
  este parámetro.

- **Violación de las expectativas del cliente**: La creación de una transacción de tipo `deposit`
  o `withdraw` no debería requerir información que no es relevante para su ejecución.

### Consecuencias de no seguir el LSP

- **Dificultad en el mantenimiento**: Si se requiere agregar un nuevo tipo de transacción, se deben
  modificar las implementaciones existentes, lo que puede introducir errores.

- **Pruebas complicadas**: Se requiere verificar que cada tipo de transacción maneje adecuadamente
  la información que no utiliza, aumentando la complejidad de las pruebas.

- **Bajo acoplamiento**: El diseño está acoplado a detalles de implementación que no son relevantes
  para todas las transacciones, lo que complica la reutilización del código.

### Solución para cumplir con el LSP

Para cumplir con el principio de sustitución de Liskov, se pueden crear clases de transacción que no
dependan de información que no es necesaria para su ejecución. En lugar de requerir la cuenta
destino en la fábrica de transacciones, se puede crear un método para manejar el tipo de transacción
que sea específico y no introduzca dependencias innecesarias.

```java
public interface TransactionBasicLsp {

  void execute(Account account);
}

@Service
public class TransactionBasicLspService {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Transactional
  public void processTransaction(String transactionType, Long accountId, BigDecimal amount) {
    Account account = accountRepository.findById(accountId)
        .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    // Utilizar la fábrica para obtener la transacción adecuada
    TransactionBasicLsp transaction = TransactionBasicLspFactory
        .createTransaction(transactionType, amount);

    transaction.execute(account);

    // Guardar la transacción en la base de datos
    Transaction trx = new Transaction();
    trx.setAccount(account);
    trx.setAmount(amount);
    trx.setType(transactionType);
    transactionRepository.save(trx);

    // Guardar la cuenta actualizada después de la transacción
    accountRepository.save(account);
  }
}

public interface TransactionMultipleLsp {

  void execute(Account accountOrigin, Account accountDestiny);
}

@Service
public class TransactionMultipleLspService {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Transactional
  public void processTransaction(String transactionType, Long accountIdOrigin,
      Long accountIdDestiny, BigDecimal amount) {
    Account accountOrigin = accountRepository.findById(accountIdOrigin)
        .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    Account accountDestiny = accountRepository.findById(accountIdDestiny)
        .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    // Utilizar la fábrica para obtener la transacción adecuada
    TransactionMultipleLsp transaction = TransactionMultipleLspFactory
        .createTransaction(transactionType, amount);

    transaction.execute(accountOrigin, accountDestiny);

    // Guardar la transacción en la base de datos - trx origen
    Transaction trxOrigen = new Transaction();
    trxOrigen.setAccount(accountOrigin);
    trxOrigen.setAmount(amount.negate());
    trxOrigen.setType(transactionType);
    transactionRepository.save(trxOrigen);

    // Guardar la transacción en la base de datos - trx destino
    Transaction trxDestiny = new Transaction();
    trxDestiny.setAccount(accountDestiny);
    trxDestiny.setAmount(amount);
    trxDestiny.setType(transactionType);
    transactionRepository.save(trxDestiny);

    // Guardar la cuenta actualizada después de la transacción
    accountRepository.save(accountOrigin);
    accountRepository.save(accountDestiny);
  }
}

public class DepositTransactionLsp implements TransactionBasicLsp {

  private BigDecimal amount;

  public DepositTransactionLsp(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public void execute(Account account) {
    BigDecimal newBalance = account.getBalance().add(amount);
    account.setBalance(newBalance);
  }
}

public class WithdrawTransactionLsp implements TransactionBasicLsp {

  private BigDecimal amount;

  public WithdrawTransactionLsp(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public void execute(Account account) {
    BigDecimal currentBalance = account.getBalance();
    if (currentBalance.compareTo(amount) >= 0) {
      BigDecimal newBalance = currentBalance.subtract(amount);
      account.setBalance(newBalance);
    } else {
      throw new IllegalArgumentException("Insufficient balance");
    }
  }
}

public class TransferTransactionLsp implements TransactionMultipleLsp {

  private BigDecimal amount;

  public TransferTransactionLsp(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public void execute(Account account, Account accountDestiny) {
    BigDecimal currentBalanceOrigin = account.getBalance();
    // Valida si el saldo es suficiente de la cuenta de origen
    if (account.getBalance().compareTo(amount) < 0) {
      throw new IllegalArgumentException("Insufficient funds");
    }

    // Actualiza el saldo de la cuenta de origen
    BigDecimal newBalanceOrigin = currentBalanceOrigin.subtract(amount);
    account.setBalance(newBalanceOrigin);

    // Actualiza el saldo de la cuenta destino
    BigDecimal currentBalanceDestiny = accountDestiny.getBalance();
    BigDecimal newBalanceDestiny = currentBalanceDestiny.add(amount);
    accountDestiny.setBalance(newBalanceDestiny);
  }
}

public class TransactionBasicLspFactory {

  public static TransactionBasicLsp createTransaction(String transactionType, BigDecimal amount) {
    switch (transactionType.toLowerCase()) {
      case "deposit":
        return new DepositTransactionLsp(amount);
      case "withdraw":
        return new WithdrawTransactionLsp(amount);
      default:
        throw new IllegalArgumentException("Unsupported transaction type: " + transactionType);
    }
  }
}

public class TransactionMultipleLspFactory {

  public static TransactionMultipleLsp createTransaction(String transactionType,
      BigDecimal amount) {
    switch (transactionType.toLowerCase()) {
      case "trasnfer":
        return new TransferTransactionLsp(amount);
      default:
        throw new IllegalArgumentException("Unsupported transaction type: " + transactionType);
    }
  }
}
```

--- 
---

#### Beneficios del LSP:

- **Facilita la extensión del código**: Las nuevas clases pueden ser añadidas sin romper la
  funcionalidad existente.
- **Aumenta la cohesión**: Las subclases deben tener un comportamiento que se ajuste a la semántica
  de la clase base.
- **Mejora la mantenibilidad**: Facilita las pruebas y la depuración al garantizar que las subclases
  no introduzcan comportamientos inesperados.

## Principio de Segregación de Interfaces (ISP)

El Principio de Segregación de Interfaces establece que ningún cliente debe verse obligado a
depender de métodos que no utiliza. Esto significa que las interfaces deben ser específicas y
adaptadas a las necesidades de los clientes, evitando interfaces grandes que obliguen a implementar
métodos innecesarios.

### Ejemplo que no cumple con el ISP:

```java
public interface BankDoesNotIspService {

  void deposit(BigDecimal amount, Long accountId);

  void withdraw(BigDecimal amount, Long accountId);

  void transfer(BigDecimal amount, Long sourceAccountId, Long targetAccountId);

  void generateMonthlyReport(Long accountId);

  void notifyCustomer(Long accountId);
}

@Service
public class TransactionDoesNotIspService implements BankDoesNotIspService {

  @Override
  public void deposit(BigDecimal amount, Long accountId) {
    // Lógica para depósito
  }

  @Override
  public void withdraw(BigDecimal amount, Long accountId) {
    // Lógica para retiro
  }

  @Override
  public void transfer(BigDecimal amount, Long sourceAccountId, Long targetAccountId) {
    // Lógica para transferencia
  }

  @Override
  public void generateMonthlyReport(Long accountId) {
    // Este servicio no necesita generar reportes, pero se ve obligado a implementar
    throw new UnsupportedOperationException("Not supported");
  }

  @Override
  public void notifyCustomer(Long accountId) {
    // Este servicio tampoco necesita notificar al cliente
    throw new UnsupportedOperationException("Not supported");
  }
}
```

### Problemas en la implementación

En este caso, el diseño no cumple con el ISP porque:

- **Implementación innecesaria**: La clase `TransactionDoesNotIspService` se ve obligada a
  implementar métodos como `generateMonthlyReport` y `notifyCustomer`, que no son relevantes para su
  funcionalidad principal.

- **Acoplamiento alto**: Al tener una interfaz grande, se crea un alto acoplamiento entre las
  implementaciones y la interfaz, lo que dificulta la modificación de las clases.

### Consecuencias de no seguir el ISP

- **Dificultad en el mantenimiento**: Cambios en la interfaz pueden requerir modificaciones en todas
  las clases que la implementan, incluso si no utilizan todos sus métodos.

- **Complejidad en las pruebas**: Las pruebas se complican, ya que es necesario verificar que se
  manejen adecuadamente los métodos no utilizados.

- **Falta de cohesión**: Las clases pueden volverse menos cohesivas, ya que están obligadas a
  manejar múltiples responsabilidades que no están relacionadas entre sí.

### Solución para cumplir con el ISP

Para cumplir con el principio de segregación de interfaces, se pueden crear interfaces más
específicas que aborden diferentes responsabilidades. Por ejemplo, se podrían crear interfaces
separadas para las operaciones bancarias y la generación de reportes.

### Ejemplo de implementación:

```java
public interface NotificationIspService {

  void notifyCustomer(Long accountId);
}

@Service
public class NotificationIspServiceImpl implements NotificationIspService {

  @Override
  public void notifyCustomer(Long accountId) {
    // Lógica para notificar al cliente
  }
}

public interface ReportIspService {

  void generateMonthlyReport(Long accountId);
}

@Service
public class ReportIspServiceImpl implements ReportIspService {

  @Override
  public void generateMonthlyReport(Long accountId) {
    // Lógica para generar reporte
  }
}

public interface TransactionIspService {

  void deposit(BigDecimal amount, Long accountId);

  void withdraw(BigDecimal amount, Long accountId);

  void transfer(BigDecimal amount, Long sourceAccountId, Long targetAccountId);
}

@Service
public class TransactionIspServiceImpl implements TransactionIspService {

  @Override
  public void deposit(BigDecimal amount, Long accountId) {
    // Lógica para depósito
  }

  @Override
  public void withdraw(BigDecimal amount, Long accountId) {
    // Lógica para retiro
  }

  @Override
  public void transfer(BigDecimal amount, Long sourceAccountId, Long targetAccountId) {
    // Lógica para transferencia
  }
}
```

### Beneficios del ISP

- **Menor acoplamiento**: Las clases pueden cambiar de forma independiente si sus interfaces son
  específicas y están bien definidas.

- **Facilidad de mantenimiento**: Al hacer cambios en una interfaz, se minimiza el impacto en otras
  clases.

- **Cohesión alta**: Las clases son más cohesivas y se enfocan en tareas específicas, lo que mejora
  la legibilidad del código.

- **Pruebas más simples**: Las pruebas se simplifican porque cada clase implementa solo los métodos
  que realmente necesita.

---

## Principio de Inversión de Dependencias (DIP)

El Principio de Inversión de Dependencias (DIP) es uno de los cinco principios SOLID que se centra
en la relación entre las clases y sus dependencias. Este principio establece que:

- **Las clases de alto nivel no deben depender de clases de bajo nivel. Ambas deben depender de
  abstracciones.**
- **Las abstracciones no deben depender de los detalles. Los detalles deben depender de las
  abstracciones.**

Esto significa que el diseño de software debe ser tal que las implementaciones concretas no estén
directamente acopladas a las clases que las utilizan, lo que permite una mayor flexibilidad y
facilidad de mantenimiento.

### Ejemplo que no cumple con el SRP:

```java

@Service
public class TransactionDoesNotDipServiceImpl {

  public void deposit(BigDecimal amount, Long accountId) {
    // Lógica para depositar dinero en la cuenta
    System.out.println("Depositing " + amount + " to account " + accountId);
  }

  public void withdraw(BigDecimal amount, Long accountId) {
    // Lógica para retirar dinero de la cuenta
    System.out.println("Withdrawing " + amount + " from account " + accountId);
  }
}
```

### Problemas en la implementación

En este caso, el diseño no cumple con el principio de Inversión de Dependencias (DIP) porque:

- **Dependencia directa de la implementación**: La clase `TransactionDoesNotDipServiceImpl` depende
  directamente de su propia implementación para realizar las operaciones de depósito y retiro, en
  lugar de depender de abstracciones (interfaces o clases abstractas).

- **Dificultad para realizar pruebas**: Al no utilizar interfaces, se complica la realización de
  pruebas unitarias, ya que no se pueden utilizar mocks o stubs para simular el comportamiento de
  las operaciones de transacción.

## Consecuencias de no seguir el DIP

- **Rigidez**: Cualquier cambio en la implementación de la clase requerirá modificaciones en el
  código que la utiliza, lo que hace que el sistema sea más rígido y menos adaptable a cambios.

- **Dificultad en la reutilización**: Sin abstracciones, la reutilización de código se vuelve más
  difícil, ya que cada clase está acoplada a su implementación concreta.

- **Aumento de la complejidad**: La falta de separación entre las capas de lógica de negocio y las
  dependencias concretas puede llevar a un aumento en la complejidad del sistema.

### Solución para cumplir con el DIP

Para cumplir con el principio de Inversión de Dependencias, se deben introducir interfaces que
definan las operaciones que deben realizarse, de modo que la
clase `TransactionDoesNotDipServiceImpl` dependa de estas abstracciones en lugar de su propia
implementación.

### Ejemplo de implementación:

```java
public interface TransactionDipService {

  void deposit(BigDecimal amount, Long accountId);

  void withdraw(BigDecimal amount, Long accountId);
}

@Service
public class TransactionDipServiceImpl implements TransactionDipService {

  @Override
  public void deposit(BigDecimal amount, Long accountId) {
    // Lógica para depositar dinero en la cuenta
    System.out.println("Depositing " + amount + " to account " + accountId);
  }

  @Override
  public void withdraw(BigDecimal amount, Long accountId) {
    // Lógica para retirar dinero de la cuenta
    System.out.println("Withdrawing " + amount + " from account " + accountId);
  }
}
```

