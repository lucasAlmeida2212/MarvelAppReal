# Plano de Correção para Fechamento Inesperado (Crash)

O aplicativo está fechando imediatamente após abrir devido a dois problemas técnicos principais identificados no código recente.

## Problemas Identificados

1.  **Crash de Ciclo de Vida no Splash:** No arquivo `Splash.java`, o método `requestWindowFeature(Window.FEATURE_NO_TITLE)` está sendo chamado após `super.onCreate(savedInstanceState)`. Em `AppCompatActivity`, isso gera uma exceção imediata porque o `AppCompatDelegate` já inicializou a janela. Além disso, como o tema do projeto já é `NoActionBar`, essa chamada é redundante.
2.  **Crash de Renderização no Custom View:** No arquivo `MarvelSplashView.java`, o componente tenta criar um `RadialGradient` usando o tamanho da View. Se o `onDraw` for chamado antes da View ser totalmente dimensionada (tamanho 0), o raio do gradiente será 0, o que causa um `IllegalArgumentException` e fecha o app.

## Alterações Propostas

### Splash
#### [MODIFY] [Splash.java](file:///C:/Users/lucascosta-ieg/AndroidStudioProjects/AppMarvel/app/src/main/java/com/example/appmarvel/Splash.java)
- Remover a chamada `requestWindowFeature(Window.FEATURE_NO_TITLE)` para evitar o crash de inicialização.
- Manter as flags de tela cheia e o timer conforme solicitado.

### Custom View
#### [MODIFY] [MarvelSplashView.java](file:///C:/Users/lucascosta-ieg/AndroidStudioProjects/AppMarvel/app/src/main/java/com/example/appmarvel/MarvelSplashView.java)
- Adicionar uma verificação de segurança no `onDraw`: se o tamanho da View for zero (ou negativo), o desenho será abortado para evitar erro no `RadialGradient`.

## Verificação

### Testes Manuais
- Executar o aplicativo e verificar se a Splash Screen permanece visível pelos 4.3 segundos configurados.
- Verificar se a transição para a `MainActivity` ocorre corretamente após o tempo determinado.
- Confirmar que não ocorrem erros no Logcat relacionados a "IllegalArgumentException" ou "requestFeature()".
