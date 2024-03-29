package br.com.dbc.vemser.pessoaapi.service;

import br.com.dbc.vemser.pessoaapi.entity.PessoaEntity;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {
  private final JavaMailSender mailSender;
  private final Configuration fmConfiguration;
  @Value("${spring.mail.username}")
  String username;
  private String from = "Hugo Ferreira <hugo.vieira@dbccompany.com.br>";

  public void sendSimpleEmail() {
    SimpleMailMessage email = new SimpleMailMessage();

    email.setFrom(from);
    email.setTo("aula.vemser@gmail.com");
    email.setSubject("aula vem ser");
    email.setText("Olá! Testando JavaMail");
    mailSender.send(email);
  }

  public void sendMailWithAttachment() throws MessagingException {
    MimeMessage email = mailSender.createMimeMessage();

    MimeMessageHelper helper = new MimeMessageHelper(email, true);

    helper.setFrom(from);
    helper.setTo("aula.vemser@gmail.com");
    helper.setSubject("email com anexo");
    helper.setText("enviando email com anexo\npulando linha");

    File file = new File("src/main/resources/images/image.jpg");
    FileSystemResource image = new FileSystemResource(file);

    helper.addAttachment(file.getName(), image);

    mailSender.send(email);
  }

  public void sendTemplateEmail(PessoaEntity pessoa) throws MessagingException {
    MimeMessage emailTemplate = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(emailTemplate, true);

    try {

      helper.setFrom(from);
      helper.setTo(pessoa.getEmail());
      helper.setSubject("email a partir de template");
      helper.setText(getContentFromTemplateExercicio1(pessoa), true);

      mailSender.send(helper.getMimeMessage());

    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }
  }

  public void enviarEmailSobreEnderecoUtilizandoTemplate(PessoaEntity pessoa, String mensagem) throws MessagingException {
    MimeMessage emailTemplate = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(emailTemplate, true);

    try {

      helper.setFrom(from);
      helper.setTo(pessoa.getEmail());
      helper.setSubject("Uma modificação foi realizada em sua conta!");
      helper.setText(enviarEmailFazerAlgoRelacionadoAoEndereco(pessoa, mensagem), true);

      mailSender.send(helper.getMimeMessage());

    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }
  }

  public String getContentFromTemplate() throws IOException, TemplateException {
    Map<String, String> dados = new HashMap<>();
    dados.put("nome", "Mayra");
    dados.put("idade", "23");
    dados.put("email", username);
    Template template = fmConfiguration.getTemplate("email-template2.ftl");
    String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);

    return html;
  }

  public String getContentFromTemplateExercicio1(PessoaEntity pessoa) throws IOException, TemplateException {
    Map<String, String> dados = new HashMap<>();
    dados.put("nome",  pessoa.getNome());
    dados.put("id", pessoa.getIdPessoa().toString());
    dados.put("email", username);
    Template template = fmConfiguration.getTemplate("email-template.ftl");
    String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);

    return html;
  }

  public String enviarEmailFazerAlgoRelacionadoAoEndereco(PessoaEntity pessoa, String mensagem) throws IOException, TemplateException {
    Map<String, String> dados = new HashMap<>();
    dados.put("nome",  pessoa.getNome());
    dados.put("mensagemPersonalizada", mensagem);
    Template template = fmConfiguration.getTemplate("email-template-endereco.ftl");
    String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);

    return html;
  }
}
