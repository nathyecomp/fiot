/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package instance.agents.controller.ifelse;

import fiot.agents.controller.Controller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author nathi_000
 * 13 August 2023
 */
public class IfElseControlGPT_V3 implements Controller {

    double sensors[];                 // Entrada de dados da rede neural;
    String sensorsName[];
    double actuador[]; //saida de dados
    String actuadorName[];
    int numInputNeurons = 0;
    int numOutputNeurons = 0;
    String name;
    String type = "";
    //name of controller on file
    String typeName = "";

    double listeningDecisionAux = 1.0;

    //   IndividualNeuralControler indi;
    public IfElseControlGPT_V3(File file) throws FileNotFoundException, IOException {
        type = "If-Else Control";
        this.processFile(file);
    }

    //Sensor_de_Luminosidade = Escuro AND Pessoa_Próxima = Não AND Dado_Recebido_Vizinho = 0.0 AND Estou_Ouvindo = Sim
    /*
    Intensidade_Lâmpada = ____Apagada____Média____Acesa
    Dado_Para_Vizinhos = ___0.0 ___0.5____1.0
    Habilitar_Audição = ___Sim ____Não
     */
    public void setPreviousListeningDecision(double value) {
        this.sensors[0] = value;
    }

    public double getPreviousListeningDecision() {
        return this.sensors[0];
    }

    public double getLightingSensor() {
        return this.sensors[1];
    }

    public double getMotionSensor() {
        return this.sensors[2];
    }

    public void setWirelessReceiver(double value) {
        this.sensors[3] = value;
    }

    public double getWirelessReceiver() {
        return this.sensors[3];
    }

    public void setWirelessTransmitter(double value) {
        this.actuador[0] = value;
    }

    public void setListeningDecision(double value) {
        this.actuador[1] = value;
    }

    public double getListeningDecision() {
        return this.actuador[1];
    }

    public void setLightDecision(double value) {
        this.actuador[2] = value;
    }

    public String getNameInput(int i) {
        return this.sensorsName[i];
    }

    public String getNameOutput(int i) {
        return this.actuadorName[i];
    }

    public double getValueInput(int i) {
        return this.sensors[i];
    }

    public double getValueOutput(int i) {
        return this.actuador[i];
    }

    public int getNumInputNeurons() {
        return numInputNeurons;
    }

    public int getNumOutputNeurons() {
        return numOutputNeurons;
    }

    private void processFile(File file) throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            line = line.replaceAll(" ", "");
            if (line.contains("<Input>")) {
                this.numInputNeurons = Integer.valueOf(line.split(":")[1]);
                this.sensors = new double[this.numInputNeurons];
                this.sensorsName = new String[this.numInputNeurons];
                // System.out.println("num road "+ this.numRoads);
                for (int cont = 0; cont < this.numInputNeurons; cont++) {
                    String line2 = bufferedReader.readLine().replaceAll(" ", "");
                    this.sensors[cont] = 0.0;
                    this.sensorsName[cont] = line2;
                }
            } else if (line.contains("<Output>")) {
                this.numOutputNeurons = Integer.valueOf(line.split(":")[1]);
                this.actuador = new double[this.numOutputNeurons];
                this.actuadorName = new String[this.numOutputNeurons];
                // System.out.println("num road "+ this.numRoads);
                for (int cont = 0; cont < this.numOutputNeurons; cont++) {
                    String line2 = bufferedReader.readLine().replaceAll(" ", "");
                    this.actuador[cont] = 0.0;
                    this.actuadorName[cont] = line2;
                }
            } else if (line.contains("<Type>")) {
                typeName = line.split(":")[1];
            }
        }
    }

    private void updateSensors(double[] input) {
        this.sensors = input;
    }



    // V3
    @Override
    public double[] getOutput(double[] input) {
        //System.out.println("Executar if..else... control GPT ");
        this.updateSensors(input);

        this.setPreviousListeningDecision(this.listeningDecisionAux);


        double lightingSensor = this.getLightingSensor();
        double motionSensor = this.getMotionSensor();
        double wirelessReceiver = (this.getPreviousListeningDecision() == 1.0) ? this.getWirelessReceiver() : 0.0;

        double lightDecision = 0.0;
        double wirelessTransmit = 0.0;
        double listeningDecision = 1.0;

        if (motionSensor == 1.0) {
            lightDecision = 1.0;
            if (this.getPreviousListeningDecision() != 1.0) {
                wirelessTransmit = 1.0; // Transmit motion detection once
                listeningDecision = 0.0; // No need to listen when we already detect motion
            }
        } else if (wirelessReceiver == 1.0) {
            lightDecision = 0.5;
            if (this.getPreviousListeningDecision() != 1.0) {
                wirelessTransmit = 0.5; // Inform neighbors about anticipatory lighting
            }
        } else if (lightingSensor == 0.0) {
            lightDecision = 0.5;
        }

        this.setLightDecision(lightDecision);
        this.setWirelessTransmitter(wirelessTransmit);
        this.setListeningDecision(listeningDecision);



        this.listeningDecisionAux = this.getListeningDecision();

        return this.actuador;
    }


    @Override
    public Controller create(File file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNameType() {
        return typeName;
    }

    @Override
    public int getNumInput() {
        return this.numInputNeurons;
    }

    @Override
    public int getNumOutput() {
        return this.numOutputNeurons;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void change(double[] configuration) {
        //nao ha adaptacao, entao simplesmente ignoro o change
        //  this.updateWeight(configuration);
    }

    @Override
    public String[] getNameInput() {
        return this.sensorsName;
    }

    @Override
    public String[] getNameOutput() {
        return this.actuadorName;
    }

}


/*
* PROMPT:
* Atividade: Vamos supor que você foi contratado para ajudar no desenvolvimento de uma aplicação para iluminação pública inteligente. O objetivo geral dessa aplicação é reduzir o consumo de energia e manter a região iluminada para os transeuntes (pessoas). Com esse propósito, um engenheiro da equipe desenvolveu um poste público com as seguintes características: sensor de luminosidade (para medir a luz ambiente), sensor de presença (para detectar a aproximação de uma pessoa), um dispositivo wireless (que permite que um poste possa conversar com o poste mais próximo) e um regulador de intensidade da lâmpada (que pode ser regulada como desligada, parcialmente acesa ou totalmente acesa).
Neste projeto não existe uma central para controlar e gerenciar os dispositivos. Cada dispositivo deve ser capaz de "agir por conta própria". Assim, cada poste público executa o seguinte ciclo em 1 segundo: (i) fase de coleta de dados a partir dos sensores (luz ambiente, presença de pessoas e dados recebidos do poste mais próximo); (ii) fase de decisão (decidir qual intensidade colocar na lâmpada (desligada, média ou alta) e decidir o quê comunicar para os postes vizinhos – ou simplesmente, decidir não comunicar) e (iii) fase de atuação (efetuar o que foi decidido, a exemplo de desligar a lâmpada). A fase de coleta e atuação já estão desenvolvidas! A sua tarefa é completar o código da fase de decisão do poste público.

Vamos considerar que as seguintes variáveis são obtidas na fase de coleta com os seguintes valores:
this.getLightingSensor() (0.0/0.5/1.0) – lembre-se que a luz do ambiente que o poste mede tem interferência da luz que ele está emitindo
this.getMotionSensor() (1.0/0.0)
this.getWirelessReceiver() (0.0/0.5/1.0) – Dado que recebe do poste mais próximo
this.getPreviousListeningDecision() (0.0/ 1.0) – Decisão que vc tomou no ciclo anterior de habilitar a audição. Se não tiver habilitado, não processa o dado do this.getWirelessReceiver().

E as seguintes variáveis têm valor atribuído durante a fase de atuação:
this.setLightDecision  (0.0/0.5/1.0)
this.setWirelessTransmitte (0.0 / 0.5 / 1.0) – Envia o dado sem saber para onde vai (alcança todos os vizinhos)
this.setListeningDecision(0.0/1.0) - Se quer ouvir algo ou não

Antes de montar a sua solução, você deve considerar que no início de cada simulação:
- A numeração dos postes pode mudar, visto que o cenário pode ser diferente em simulações diferentes (Ver exemplo de cenário na apresentação)
- Três lâmpadas podem quebrar de forma aleatória antes de iniciar a simulação
- Uma pessoa tem uma rota pra seguir (com um ponto de origem e destino) e deseja concluir sua rota dentro de no máximo 12 segundos (ver apresentação) – Esse é o fator mais importante para uma boa solução!
- A velocidade do transeunte é proporcional à luminosidade da área onde ele está e da área para onde ele vai (se as duas estiverem escuras, a pessoa simplesmente não anda)
- O consumo de energia é proporcional à intensidade da lâmpada (0.0/ 0.5 / 1.0)
- Se o poste decidir falar algo diferente de "0.0", ele gasta 0.1 de energia

Então, o objetivo é que você gere a sua versão para a fase de decisão que será executada por cada um dos postes públicos, considerando as variáveis de entrada e de saída. O objetivo total é reduzir o consumo de energia e diminuir o tempo de viagem das pessoas (ao término da simulação de 12 segundos).
* */

/*
* ANSWER
*
* Entendendo as novas especificações fornecidas, podemos propor o seguinte algoritmo para a fase de decisão do poste:

### Fase de Decisão:

1. **Avalie a Luminosidade Ambiente:**
    - Se estiver claro (1.0), não há necessidade de iluminação. Mas vamos definir que sempre estaremos "escutando" (listen) para sermos informados se há alguma movimentação nas proximidades.

2. **Detecte Movimento:**
    - Se uma pessoa for detectada, acenda a lâmpada totalmente (1.0) e informe os postes vizinhos sobre o movimento detectado.

3. **Comunicação Wireless com Postes Vizinhos:**
    - Se o poste anterior nos informou de um movimento e estivermos no modo de escuta, acenda a lâmpada em intensidade média (0.5) para preparar o caminho para a pessoa. Se já estivermos em intensidade 1.0 devido à detecção de movimento, mantenha essa intensidade.

4. **Economia de Energia:**
    - Se não houver movimento detectado por um tempo (por exemplo, 3 ciclos), reduza a intensidade para economizar energia.

Traduzindo em código:

```java
public void decisionPhase() {
    // Variáveis de entrada
    double lightingSensor = this.getLightingSensor();
    double motionSensor = this.getMotionSensor();
    double wirelessReceiver = (this.getPreviousListeningDecision() == 1.0) ? this.getWirelessReceiver() : 0.0;  // Se não estiver escutando, ignora o valor.

    // Variáveis de decisão
    double lightDecision = 0.0;  // Por padrão, mantemos desligado
    double wirelessTransmit = 0.0;  // Por padrão, não transmitimos nada
    double listeningDecision = 1.0;  // Por padrão, sempre ouvimos os postes ao redor

    // Se for dia, mantemos a lâmpada desligada
    if (lightingSensor == 1.0) {
        lightDecision = 0.0;
    } else {
        // Se detectarmos movimento
        if (motionSensor == 1.0) {
            lightDecision = 1.0;
            wirelessTransmit = 1.0;  // Avisamos os postes ao redor do movimento detectado
        }
        // Se o poste vizinho detectou movimento e nós estivermos escutando
        else if (wirelessReceiver > 0.0) {
            lightDecision = 0.5;  // Acendemos parcialmente para preparar o caminho
        }
    }

    // Decisões de atuação
    this.setLightDecision(lightDecision);
    this.setWirelessTransmitte(wirelessTransmit);
    this.setListeningDecision(listeningDecision);
}
```

Esse é um algoritmo básico. Pode ser necessário adicionar mais lógica, como um contador para rastrear quantos ciclos se passaram desde a última detecção de movimento, para poder reduzir a intensidade da luz e economizar energia. Por fim, testes em um ambiente simulado seriam ideais para refinar ainda mais o algoritmo.

* First Result:
* //FITNESS = -17
*
* Prompt 2: the result for this solution is not good
*
* Response 2:
* Peço desculpa pelo incômodo. Vamos abordar de uma forma mais otimizada considerando o cenário apresentado:

A ideia geral é:
- Maximizar o uso eficiente da iluminação (usar luz apenas quando necessário).
- Minimizar o consumo de energia de transmissão.
- Priorizar o movimento do transeunte.

```java
public void decisionPhase() {
    double lightingSensor = this.getLightingSensor();
    double motionSensor = this.getMotionSensor();
    double wirelessReceiver = (this.getPreviousListeningDecision() == 1.0) ? this.getWirelessReceiver() : 0.0;

    double lightDecision = 0.0;
    double wirelessTransmit = 0.0;
    double listeningDecision = 1.0;  // Estará sempre escutando para otimizar o caminho da pessoa

    // Se detectarmos movimento
    if (motionSensor == 1.0) {
        lightDecision = 1.0;  // Ligue totalmente para o transeunte
        wirelessTransmit = 1.0;  // Avisar os postes próximos para preparar o caminho

    // Se ouvir de um poste vizinho sobre movimento
    } else if (wirelessReceiver == 1.0) {
        lightDecision = 0.5;  // Acenda parcialmente para antecipar a chegada do transeunte

    // Economia de energia sem comprometer a segurança
    } else if (lightingSensor == 0.0) {
        lightDecision = 0.5;  // Manter aceso parcialmente se estiver muito escuro, para garantir algum nível de segurança
    }

    // Decisões de atuação
    this.setLightDecision(lightDecision);
    this.setWirelessTransmitte(wirelessTransmit);
    this.setListeningDecision(listeningDecision);
}
```

A lógica está baseada nos seguintes pontos:

1. **Prioridade Máxima para o Transeunte:** Quando o sensor de movimento detecta alguém, acende totalmente e informa os postes vizinhos.
2. **Preparação Antecipada:** Ao ouvir de um poste vizinho sobre o movimento, acende parcialmente para antecipar a chegada do transeunte.
3. **Economia com Segurança:** Se estiver muito escuro e nenhuma das condições anteriores for atendida, mantém a luz parcialmente acesa para garantir segurança.

Com essa lógica, esperamos minimizar a transmissão sem comprometer a segurança ou o conforto do transeunte. O movimento do transeunte é a prioridade máxima e, por isso, garantimos um caminho iluminado para ele. Adicionalmente, mantemos um nível mínimo de iluminação quando está muito escuro para garantir segurança.

A eficácia real do código depende de muitos fatores, incluindo a disposição dos postes, a frequência e os padrões de movimento dos transeuntes e mais. Idealmente, o código seria iterado e testado em simulações para melhorar ainda mais a sua eficácia.
*
*
*/
/* RESULT WITH SOLUTION 2:

        Fitness 52.395959595959596
Energy 41.01010101010101
People 100.0
totalFTrip 52.0


New scenario:
Fitness 67.53030303030303
Energy 22.840909090909097
People 100.0
totalFTrip 38.888888888888886
        * */

// VERSAO DO SISTEMA - O SISTEMA OBSERVADOR ADAPTANDO O CONTROLLER DO AGENTE DE ACORDO COM O RESULTADO DA SIMULACAO. PROPOS PRIMEIRO MODELO, FITNESS FOI BAIXO, ENTAO
//PEDIU NOVA VERSAO DO MODELO; TESTOU E FITNESS FOI ALTO.
// PROXIMA PERGUNTA: COULD YOU IMPROVE IT? TESTAR TERCEIRA SOLUÇAO

/*
Prompt 3: Could you improve it to improve the simulation results?
Result:
Certainly! Let's try to optimize the decision-making process further. We need to be smart about how each post communicates to minimize energy consumption, and how it responds to both its sensors and the wireless data from neighboring posts.

Here's an optimized strategy:

1. **Immediate Proximity Response**: If a motion is detected in the immediate vicinity of the post, it should light up fully.
2. **Anticipatory Lighting**: If a neighboring post reports motion, we should light up but at half intensity to anticipate someone's approach. But, if the current post already knows about the movement (from the previous cycle), it doesn't need to transmit the information again.
3. **Minimize Data Transmission**: To avoid the cost of wireless data transmission, the post should only transmit when a motion is detected for the first time and not continuously.
4. **Determine Need for Listening**: If a post is already fully lit (indicating immediate proximity of a person), it doesn't need to listen to neighbors.
5. **Adaptive Dimming**: If the lighting sensor indicates it's dark and no motion has been detected or reported by neighbors, it might light up at half intensity for safety.

Let's translate this into code:

```java
public void decisionPhase() {
    double lightingSensor = this.getLightingSensor();
    double motionSensor = this.getMotionSensor();
    double wirelessReceiver = (this.getPreviousListeningDecision() == 1.0) ? this.getWirelessReceiver() : 0.0;

    double lightDecision = 0.0;
    double wirelessTransmit = 0.0;
    double listeningDecision = 1.0;

    if (motionSensor == 1.0) {
        lightDecision = 1.0;
        if (this.getPreviousListeningDecision() != 1.0) {
            wirelessTransmit = 1.0; // Transmit motion detection once
            listeningDecision = 0.0; // No need to listen when we already detect motion
        }
    } else if (wirelessReceiver == 1.0) {
        lightDecision = 0.5;
        if (this.getPreviousListeningDecision() != 1.0) {
            wirelessTransmit = 0.5; // Inform neighbors about anticipatory lighting
        }
    } else if (lightingSensor == 0.0) {
        lightDecision = 0.5;
    }

    this.setLightDecision(lightDecision);
    this.setWirelessTransmitte(wirelessTransmit);
    this.setListeningDecision(listeningDecision);
}
```

This approach minimizes data transmission to save energy, adjusts light intensity dynamically to ensure a person's safety, and reduces redundancy by having posts not listen when they already detect motion. Testing this approach in simulations can provide insights on further optimizations.



* Result:
* Fitness 65.95454545454545
Energy 21.78030303030303
People 100.0
totalFTrip 42.22222222222222

Fitness 64.67171717171718
Energy 21.654040404040405
People 100.0
totalFTrip 44.44444444444444
* */