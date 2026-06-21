/*
Um login simples, tendo 3 tentativas se passar disso o botão trava e aparece conta bloqueada

*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Bloco_de_notas {
    static String user = "";
    static String senha_certa = "";
    static int tentativas = 3;
    static  int time_block = 15;
    static Timer espera;

    public static void main (String[] args){

        JFrame tela = new JFrame("Login");
        tela.setSize(500,500);
        tela.setLayout(null);
        tela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tela.getContentPane().setBackground(new Color(160,82,45));

        JPasswordField senha_digitada = new JPasswordField();
        senha_digitada.setBounds(100, 150,100,50);
        senha_digitada.setBackground(new Color(255,69,0));
        senha_digitada.setForeground(Color.black);

        JTextField admin = new JTextField();
        admin.setBounds(100, 50,100,50);
        admin.setBackground(new Color(255,69,0));
        admin.setForeground(Color.black);

        JLabel caixa = new JLabel();
        caixa.setBounds(125,220,250,60);
        caixa.setForeground(Color.WHITE);

        JButton enter = new JButton("enter");
        enter.setBounds(300,50,100,50);
        enter.setBackground(new Color(205,92,92));
        enter.setForeground(Color.YELLOW);

        JButton cadastre = new JButton("Cadastrar");
        cadastre.setBounds(300,150,100,50);
        cadastre.setBackground(new Color(205,92,92));
        cadastre.setForeground(Color.YELLOW);

        cadastre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame cadastrar = new JFrame("CADASTRO");
                cadastrar.setSize(500,500);
                cadastrar.setLayout(null);
                cadastrar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                cadastrar.getContentPane().setBackground(new Color(3, 63, 160));

                JPasswordField newsenha_certa = new JPasswordField();
                newsenha_certa.setBounds(100,150,100,50);
                newsenha_certa.setBackground(new Color(87, 94, 170));
                newsenha_certa.setForeground(new Color(0, 210, 250));

                JTextField newuser = new JTextField();
                newuser.setBounds(100,50,100,50);
                newuser.setBackground(new Color(87, 94, 170));
                newuser.setForeground(new Color(0, 210, 250));

                JLabel erro = new JLabel();
                erro.setBounds(250, 250, 100, 50);

                JButton salve = new JButton("Salvar");
                salve.setBounds(300,50,100,50);
                salve.setBackground(new Color(87, 94, 170));
                salve.setForeground(Color.YELLOW);
                salve.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String senha = new String(newsenha_certa.getPassword());
                        String test_user = new String(newuser.getText());

                        if(newuser.getText().equals("") || senha.equals("")){
                            erro.setText("Caracteres invalidos");
                        }else {

                            boolean saved = false;
                            try {

                                FileReader leitor = new FileReader("usuario.txt");
                                BufferedReader buffleitor = new BufferedReader(leitor);
                                String linha;

                                while ((linha = buffleitor.readLine()) != null){
                                    String[] dados = linha.split(";");
                                    if (test_user.equals(dados[0])){
                                        saved = true;
                                        break;
                                    }

                                }
                                buffleitor.close();

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                            if(saved){
                                erro.setText("Usuario ja existe tente outro");
                            }else {
                                try {

                                    user = newuser.getText();
                                    senha_certa = senha;

                                    FileWriter copista = new FileWriter("usuario.txt", true);
                                    BufferedWriter buffer = new BufferedWriter(copista);
                                    buffer.write(user+";"+senha_certa);
                                    buffer.newLine();
                                    buffer.close();

                                    cadastrar.dispose();

                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }

                        }
                    }
                });

                cadastrar.add(erro);
                cadastrar.add(salve);
                cadastrar.add(newsenha_certa);
                cadastrar.add(newuser);

                cadastrar.setVisible(true);

            }
        });

        espera = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                time_block--;

                if( time_block > 0){

                    caixa.setText("CONTA BLOQUEADA, espere "+time_block+"s");

                }else {

                    espera.stop();
                    enter.setEnabled(true);
                    caixa.setText("Tentativas zeradas");
                    tentativas = 0;
                    time_block = 15;

                }
            }
        });

        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String adm = new String(admin.getText());
                String senha_dig = new String(senha_digitada.getPassword());
                boolean conect = false;

                try {
                    FileReader read = new FileReader("usuario.txt");
                    BufferedReader buffread = new BufferedReader(read);

                    String linha;


                    while ((linha = buffread.readLine()) != null){

                        String[] archive = linha.split(";");
                        String user_save = archive[0];
                        String password_save = archive[1];

                        if(senha_dig.equals(password_save) && adm.equals(user_save)){

                            conect = true;
                            break;

                        }

                    }
                    buffread.close();
                } catch (Exception e) {
                    caixa.setText("Erro ao procurar usuario");
                }

                if(conect){
                    caixa.setForeground(Color.GREEN);
                    caixa.setText("Conectado ");
                    tentativas = 3;

                    tela.setVisible(false);

                    JFrame texto = new JFrame();
                    texto.setSize(600, 400);
                    texto.getContentPane().setBackground(Color.DARK_GRAY);
                    texto.setLayout(null);
                    texto.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    JTextArea write = new JTextArea();
                    write.setBounds(50,50,480,250);
                    write.setLineWrap(true);
                    write.setBackground(new Color(57, 57, 57));
                    write.setForeground(Color.WHITE);
                    write.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                    try {
                        FileReader leia = new FileReader("notas_" +adm+ ".txt");
                        BufferedReader buffleia = new BufferedReader(leia);

                        String line;

                        while ((line = buffleia.readLine()) != null){

                            write.append(line+"\n");

                        }

                        buffleia.close();
                    } catch (Exception e) {
                        System.out.println("Primeiro acesso!");
                    }

                    JButton salvar = new JButton("SALVAR");
                    salvar.setBounds(25,0, 100,50);
                    salvar.setBackground(new Color(73, 63, 63, 255));
                    salvar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            try {
                                FileWriter notes = new FileWriter("notas_"+adm+".txt");
                                BufferedWriter buffnotes = new BufferedWriter(notes);

                                buffnotes.write(write.getText());

                                buffnotes.close();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                            texto.dispose();
                            tela.setVisible(true);
                        }
                    });

                    texto.add(write);
                    texto.add(salvar);

                    texto.setVisible(true);

                }


                else{

                    tentativas--;
                    caixa.setForeground(Color.DARK_GRAY);
                    caixa.setText("Senha invalida e/ou user, você ainda tem "+tentativas+" tentativas");

                    if(tentativas <= 0){

                        enter.setEnabled(false);
                        caixa.setForeground(Color.RED);
                        caixa.setText("CONTA BLOQUEADA");
                        espera.start();

                    }

                }

            }

        });

        senha_digitada.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {

                if(e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER && enter.isEnabled()){
                    enter.doClick();
                }

                if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && enter.isEnabled()){
                    admin.setText("");
                    senha_digitada.setText("");
                }

                if(e.getKeyCode() == KeyEvent.VK_END && enter.isEnabled()){
                    senha_digitada.setEchoChar((char) 0);
                }if(e.getKeyCode() == KeyEvent.VK_HOME && enter.isEnabled()){
                    senha_digitada.setEchoChar('\u2022');
                }
            }
        });

        tela.add(admin);
        tela.add(caixa);
        tela.add(senha_digitada);
        tela.add(enter);
        tela.add(cadastre);

        tela.setVisible(true);

    }
}