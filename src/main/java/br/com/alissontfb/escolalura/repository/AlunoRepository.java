package br.com.alissontfb.escolalura.repository;

import br.com.alissontfb.escolalura.codec.AlunoCodec;
import br.com.alissontfb.escolalura.model.Aluno;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.stereotype.Repository;

@Repository
public class AlunoRepository {

    public void salvar(Aluno aluno){
        Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);
        AlunoCodec alunoCodec = new AlunoCodec(codec);
        CodecRegistry registry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(), CodecRegistries.fromCodecs(alunoCodec));

        MongoClientOptions options = MongoClientOptions.builder().codecRegistry(registry).build();

        MongoClient client = new MongoClient("localhost:27017", options);
        MongoDatabase db = client.getDatabase("curso");
        MongoCollection<Aluno> alunos = db.getCollection("alunos", Aluno.class);
        alunos.insertOne(aluno);

        client.close();
    }

}
