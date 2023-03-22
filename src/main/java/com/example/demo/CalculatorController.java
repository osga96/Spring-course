package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalculatorController {

  @GetMapping("/calculator")
  public String calculator(
      @RequestParam(name = "num1", required = false, defaultValue = "0") String num1,
      @RequestParam(name = "num2", required = false, defaultValue = "0") String num2,
      @RequestParam(name = "op", required = false, defaultValue = "+") String op,
      Model view) {
    int myNum1 = Integer.parseInt(num1);
    double myNum2 = Double.parseDouble(num2);
    double result = 0;
    switch (op) {
      case "+":
        result = myNum1 + myNum2;
        break;
      case "-":
        result = myNum1 - myNum2;
        break;
      case "*":
        result = myNum1 * myNum2;
        break;
      case "/":
        result = myNum1 / myNum2;
        break;
      default:
        break;
    }
    // limit result to 2 decimal places
    result = Math.round(result * 100.0) / 100.0;
    view.addAttribute("result", result);
    view.addAttribute("title", "Hola mundo!");
    view.addAttribute("num1", num1);
    view.addAttribute("num2", num2);
    view.addAttribute("op", op);
    return "calculator";
  }

  @GetMapping("/evaluate")
  public String evaluate(
      @RequestParam(name = "exp", defaultValue = "2+3=") String expression, Model view) {
    view.addAttribute("expression", expression);

    int number1;
    double number2;
    double result = 0.0;

    boolean firstNumber = true;

    char[] expressionArray = expression.toCharArray();

    StringBuilder number1String = new StringBuilder();
    StringBuilder number2String = new StringBuilder();
    String operandString = figureOutNumbersAndOperand(expression, firstNumber, expressionArray, number1String, number2String);


    number1 = Integer.parseInt(String.valueOf(number1String));
    number2 = Double.parseDouble(String.valueOf(number2String));

    switch (operandString) {
      case "+":
        result = number1 + number2;
        break;
      case "-":
        result = number1 - number2;
        break;
      case "*":
        result = number1 * number2;
        break;
      case "/":
        result = number1 / number2;
        break;
      case "^":
        result = Math.pow(number1, number2);
        break;
      default:
        break;
    }

    view.addAttribute("result", result);

    return "evaluate";
  }

  private String figureOutNumbersAndOperand(String expression, boolean firstNumber, char[] expressionArray,
      StringBuilder number1String, StringBuilder number2String) {
    String operandString = "";
    for (int i = 0; i < expression.length(); i++) {

      if (expressionArray[i] != '=') {
        if (!firstNumber) {
          number2String.append(expressionArray[i]);
        }

        if (Character.isDigit(expressionArray[i]) && firstNumber) {
          number1String.append(expressionArray[i]);
        } else {
          firstNumber = false;

          switch (expressionArray[i]) {
            case '+':
              operandString = "+";
              break;
            case '-':
              operandString = "-";
              break;
            case '*':
              operandString = "*";
              break;
            case '/':
              operandString = "/";
              break;
            case '^':
              operandString = "^";
              break;
            default:
              break;
          }
        }
      }

    }
    return operandString;
  }
}
