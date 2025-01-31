package br.com.lrostech.nfce_teste.domain.contract;

import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.Evento;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema.envEventoCancNFe.TEnvEvento;
import br.com.swconsultoria.nfe.schema.envEventoCancNFe.TRetEnvEvento;
import br.com.swconsultoria.nfe.schema_4.inutNFe.TInutNFe;
import br.com.swconsultoria.nfe.schema_4.inutNFe.TRetInutNFe;
import br.com.swconsultoria.nfe.schema_4.retConsSitNFe.TRetConsSitNFe;
import br.com.swconsultoria.nfe.schema_4.retConsStatServ.TRetConsStatServ;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TEnviNFe;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TRetEnviNFe;

import javax.xml.bind.JAXBException;
import java.time.LocalDateTime;

public interface INfeLib {
    ConfiguracoesNfe criarConfiguracoes(EstadosEnum estado, AmbienteEnum ambiente, Certificado certificado, String pastaSchemas) throws CertificadoException;
    TRetConsStatServ statusServico(ConfiguracoesNfe configuracoes, DocumentoEnum tipoDocumento) throws NfeException;
    TRetConsSitNFe consultaXml(ConfiguracoesNfe configuracoes, String chave, DocumentoEnum tipoDocumento) throws NfeException;
    TInutNFe montaInutilizacao(DocumentoEnum tipoDocumento, String cnpj, int serie, int numeroInicial, int numeroFinal, String justificativa, LocalDateTime dataInutilizacao, ConfiguracoesNfe configuracoes) throws NfeException;
    TRetInutNFe inutilizacao(ConfiguracoesNfe configuracoes, TInutNFe inutNFe, DocumentoEnum tipoDocumento, boolean validar) throws NfeException;
    void validaInutilizacao(TRetInutNFe retorno) throws NfeException;
    TEnvEvento montaCancelamento(Evento eventoCancelamento, ConfiguracoesNfe configuracoes) throws NfeException;
    TRetEnvEvento cancelarNfe(ConfiguracoesNfe configuracoes, TEnvEvento tEnvEvento, boolean valida, DocumentoEnum tipoDocumento) throws NfeException;
    void validaCancelamento(TRetEnvEvento retorno) throws NfeException;
    br.com.swconsultoria.nfe.schema.envEventoCancSubst.TEnvEvento montaCancelamentoSubstituicao(Evento eventoCancelamento, ConfiguracoesNfe configuracoes) throws NfeException;
    br.com.swconsultoria.nfe.schema.envEventoCancSubst.TRetEnvEvento cancelarSubstituicaoNfe(ConfiguracoesNfe configuracoes, br.com.swconsultoria.nfe.schema.envEventoCancSubst.TEnvEvento eventoCancelamento, boolean valida) throws NfeException;
    void validaCancelamentoSubstituicao(br.com.swconsultoria.nfe.schema.envEventoCancSubst.TRetEnvEvento retorno) throws NfeException;
    TEnviNFe xmlToObject(String xml, Class<?> classe) throws JAXBException;
    TEnviNFe montaNfe(ConfiguracoesNfe configuracoes, TEnviNFe enviNFe, boolean valida) throws NfeException;
    TRetEnviNFe enviarNfe(ConfiguracoesNfe configuracoes, TEnviNFe enviNFe, DocumentoEnum tipoDocumento) throws NfeException;
}
