package calculation.components;

import calculation.operation.FunctionInterface;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.BiFunction;

@Slf4j
@NoArgsConstructor
@Component
public class Calculator {

    private static final String PACKAGE_NAME = "calculation.operation.impl";
    private static final String METHOD_NAME = "getOperationSign";

    /**
     * This method returns operation object based on given operation sign such as
     * "+", "-", "*" and "/".
     * The operation object implementation should be defined in package folder "calculation.operation.impl".
     * e.g. The implementation Addition returns if operationSign is "+", otherwise no implementation returns.
     *
     * @param operationSign  the operation sign such as "+" "-" "*" or "/"
     * @return The Optional object which contains operation implementation. returns empty if no implementation.
     */
    @SneakyThrows
    public Optional<FunctionInterface> getOperation(String operationSign) {
        ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
        ImmutableSet<ClassPath.ClassInfo> classes = classPath.getTopLevelClasses(PACKAGE_NAME);

        Optional<ClassPath.ClassInfo> classInfoOptional = classes.stream().filter((classInfo) -> {
            FunctionInterface obj = createNewObject(classInfo);
            Optional<String> operationOptional = getDeclaredMethod(obj);
            if (operationOptional.isPresent()) {
                return operationOptional.get().equals(operationSign);
            } else {
                log.error("Error occurred while fetching the value of operation");
                return false;
            }

        }).findFirst();

        if (classInfoOptional.isPresent()) {
            return Optional.of((FunctionInterface) createNewObject(classInfoOptional.get()));
        } else {
            return Optional.empty();
        }
    }

    /**
     * This is the generic method which is being used by basic arithmetic operations.
     * it will be used in the future for the other more complex ones.
     *
     * @param operand1          the operand one
     * @param operand2          the operand two
     * @param calculateFunction this is the function method, currently only support 4
     *                          operations: addition, division, multiplication and subtraction.
     * @param <R>  this method is generic method
     * @return returned value is the result of the calculation of operand 1 and operand
     * 2 based on the given function method.
     */
    public <R> R getResult(Double operand1, Double operand2, BiFunction<Double, Double, R> calculateFunction) {
        return calculateFunction.apply(operand1, operand2);
    }

    private Optional<String> getDeclaredMethod(FunctionInterface obj) {

        try {
            Method method = obj.getClass().getDeclaredMethod(METHOD_NAME);
            String operationSymbol = (String) method.invoke(obj);
            return Optional.of(operationSymbol);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.error("No method:" + METHOD_NAME + " in object: " + obj.getClass().getSimpleName());
            return Optional.empty();
        }
    }

    @SneakyThrows
    private FunctionInterface createNewObject(ClassPath.ClassInfo classInfo) {
        return (FunctionInterface) Class.forName(classInfo.getName()).newInstance();
    }

}
