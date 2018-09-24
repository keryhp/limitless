package org.uk.ubs.limitless.service.nlg;

import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SampleNLP {

    private static Logger LOG = Logger.getLogger(SampleNLP.class);

    public void runAnnotators(String url) {

        Properties props = new Properties();

        //props.setProperty("annotators","tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // read rss news article text in the text variable
        StringBuilder text = readFile(url);

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text.toString());

        // run all Annotators on this text
        pipeline.annotate(document);

        // these are all the sentences in this document
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        List<String> words = new ArrayList<>();
        List<String> posTags = new ArrayList<>();
        List<String> nerTags = new ArrayList<>();
        for (CoreMap sentence : sentences) {
            // traversing the words in the current sentence
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                words.add(word);
                // this is the POS tag of the token
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                posTags.add(pos);

                // this is the NER label of the token
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                nerTags.add(ne);

                // This is the syntactic parse tree of sentence
                Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
                //LOG.info("Tree:\n" + tree);

                // This is the dependency graph of the sentence
                SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedDependenciesAnnotation.class);
                //LOG.info("Dependencies\n:"+ dependencies);
            }

            // This is a map of the chain
            Map<Integer, CorefChain> graph = document.get(CorefCoreAnnotations.CorefChainAnnotation.class);
            //LOG.info("Map of the chain:\n" + graph);
        }
        LOG.info("Words:\n" + words);
        LOG.info("POSTags:\n" + posTags);
        LOG.info("NERTags:\n" + nerTags);
        //writeToFile();
        LOG.info("End of Processing Annotater for file");
    }

    private StringBuilder readFile(String url) {
        File file = new File(url);
        StringBuilder contents = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String text = null;
            while ((text = reader.readLine()) != null) {
                contents.append(text).append(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }

    private void writeToFile(String text, String fileName) {
        File file = new File(fileName + "_nlp");
        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
