package br.com.alissontfb.escolalura.codec;

import br.com.alissontfb.escolalura.model.Aluno;
import br.com.alissontfb.escolalura.model.Curso;
import org.bson.*;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import java.util.Date;

public class AlunoCodec implements CollectibleCodec<Aluno> {

    private Codec<Document> codec;

    public AlunoCodec(Codec<Document> codec) {
        this.codec = codec;
    }

    @Override
    public Aluno generateIdIfAbsentFromDocument(Aluno aluno) {
        return documentHasId(aluno) ? aluno.criarId(): aluno;
    }

    @Override
    public boolean documentHasId(Aluno aluno) {
        return aluno.getId() == null;
    }

    @Override
    public BsonValue getDocumentId(Aluno aluno) {
        if (!documentHasId(aluno)){
            throw new IllegalStateException("Esse documento nao tem ID");
        }

        return new BsonString(aluno.getId().toHexString());
    }

    @Override
    public Aluno decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return null;
    }

    @Override
    public void encode(BsonWriter bsonWriter, Aluno aluno, EncoderContext encoderContext) {

        ObjectId id = aluno.getId();
        String nome = aluno.getNome();
        Date dataNascimento = aluno.getDataNascimento();
        Curso curso = aluno.getCurso();



        Document document = new Document();
        document.put("_id", id);
        document.put("nome", nome);
        document.put("data_nascimento", dataNascimento);
        document.put("curso", new Document("nome", curso.getNome()));
//        document.put("notas", aluno.getNotas());
//        document.put("habilidades", aluno.getId());

        codec.encode(bsonWriter, document, encoderContext);
    }

    @Override
    public Class<Aluno> getEncoderClass() {
        return Aluno.class;
    }
}
