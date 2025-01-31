package br.com.lrostech.nfce_teste.infra;

import br.com.lrostech.nfce_teste.domain.contract.INfeLib;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.Evento;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema.envEventoCancNFe.TEnvEvento;
import br.com.swconsultoria.nfe.schema.envEventoCancNFe.TRetEnvEvento;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TEnviNFe;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TRetEnviNFe;
import br.com.swconsultoria.nfe.schema_4.inutNFe.TInutNFe;
import br.com.swconsultoria.nfe.schema_4.inutNFe.TRetInutNFe;
import br.com.swconsultoria.nfe.schema_4.retConsSitNFe.TRetConsSitNFe;
import br.com.swconsultoria.nfe.schema_4.retConsStatServ.TRetConsStatServ;
import br.com.swconsultoria.nfe.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class NfeLib implements INfeLib {
    @Override
    public ConfiguracoesNfe criarConfiguracoes(EstadosEnum estado, AmbienteEnum ambiente, Certificado certificado, String pastaSchemas) throws CertificadoException {
        return ConfiguracoesNfe.criarConfiguracoes(estado, ambiente, certificado, pastaSchemas);
    }

    @Override
    public TRetConsStatServ statusServico(ConfiguracoesNfe configuracoes, DocumentoEnum tipoDocumento) throws NfeException {
        return Nfe.statusServico(configuracoes, tipoDocumento);
    }

    @Override
    public TRetConsSitNFe consultaXml(ConfiguracoesNfe configuracoes, String chave, DocumentoEnum tipoDocumento) throws NfeException {
        return Nfe.consultaXml(configuracoes, chave, tipoDocumento);
    }

    @Override
    public TInutNFe montaInutilizacao(DocumentoEnum tipoDocumento, String cnpj, int serie, int numeroInicial, int numeroFinal, String justificativa, LocalDateTime dataInutilizacao, ConfiguracoesNfe configuracoes) throws NfeException {
        return InutilizacaoUtil.montaInutilizacao(tipoDocumento, cnpj, serie, numeroInicial, numeroFinal, justificativa, dataInutilizacao, configuracoes);
    }

    @Override
    public TRetInutNFe inutilizacao(ConfiguracoesNfe configuracoes, TInutNFe inutNFe, DocumentoEnum tipoDocumento, boolean validar) throws NfeException {
        return Nfe.inutilizacao(configuracoes, inutNFe, tipoDocumento, validar);
    }

    @Override
    public void validaInutilizacao(TRetInutNFe retorno) throws NfeException {
        RetornoUtil.validaInutilizacao(retorno);
    }

    @Override
    public TEnvEvento montaCancelamento(Evento eventoCancelamento, ConfiguracoesNfe configuracoes) throws NfeException {
        return CancelamentoUtil.montaCancelamento(eventoCancelamento, configuracoes);
    }

    @Override
    public TRetEnvEvento cancelarNfe(ConfiguracoesNfe configuracoes, TEnvEvento tEnvEvento, boolean valida, DocumentoEnum tipoDocumento) throws NfeException {
        return Nfe.cancelarNfe(configuracoes, tEnvEvento, valida, tipoDocumento);
    }

    @Override
    public void validaCancelamento(TRetEnvEvento retorno) throws NfeException {
        RetornoUtil.validaCancelamento(retorno);
    }

    @Override
    public br.com.swconsultoria.nfe.schema.envEventoCancSubst.TEnvEvento montaCancelamentoSubstituicao(Evento eventoCancelamento, ConfiguracoesNfe configuracoes) throws NfeException {
        return CancelamentoSubstituicaoUtil.montaCancelamento(eventoCancelamento, configuracoes);
    }

    @Override
    public br.com.swconsultoria.nfe.schema.envEventoCancSubst.TRetEnvEvento cancelarSubstituicaoNfe(ConfiguracoesNfe configuracoes, br.com.swconsultoria.nfe.schema.envEventoCancSubst.TEnvEvento eventoCancelamento, boolean valida) throws NfeException {
        return Nfe.cancelarSubstituicaoNfe(configuracoes, eventoCancelamento, valida);
    }

    @Override
    public void validaCancelamentoSubstituicao(br.com.swconsultoria.nfe.schema.envEventoCancSubst.TRetEnvEvento retorno) throws NfeException {
        RetornoUtil.validaCancelamentoSubstituicao(retorno);
    }

    @Override
    public TEnviNFe xmlToObject(String xml, Class<?> classe) throws JAXBException {
        return XmlNfeUtil.xmlToObject(xml, TEnviNFe.class);
    }


    @Override
    public TEnviNFe montaNfe(ConfiguracoesNfe configuracoes, TEnviNFe enviNFe, boolean valida) throws NfeException {
        return Nfe.montaNfe(configuracoes, enviNFe, valida);
    }

    @Override
    public TRetEnviNFe enviarNfe(ConfiguracoesNfe configuracoes, TEnviNFe enviNFe, DocumentoEnum tipoDocumento) throws NfeException {
        return Nfe.enviarNfe(configuracoes, enviNFe, tipoDocumento);
    }
}
