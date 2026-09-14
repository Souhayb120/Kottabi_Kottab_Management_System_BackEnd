package com.example.kottabi.controller;

import com.example.kottabi.DTO.AI_DTO.AiRapportRequestDTO;
import com.example.kottabi.DTO.AI_DTO.AiRapportResponceDTO;
import com.example.kottabi.services.AIRapportGenerator;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/rapport")
public class AiController {

	private final AIRapportGenerator aiRapportGenerator;
	private final ChatClient chatClient;

	public AiController(AIRapportGenerator aiRapportGenerator, ChatClient.Builder chatClientBuilder) {
		this.aiRapportGenerator = aiRapportGenerator;
		this.chatClient = chatClientBuilder.build();
	}

	@GetMapping("/ask")
	public String ask(@RequestParam String ask) {
		return chatClient.prompt().user(ask).call().content();
	}

	@PostMapping("/{id}")
	public AiRapportResponceDTO sendRapport(@PathVariable long id) {
		AiRapportRequestDTO aiRapportRequestDTO = aiRapportGenerator.createEleveRapportAi(id);
		return chatClient
			.prompt(
				"Analyze this student's complete history.\n" +
				"\n" +
				"Based only on the information provided:\n" +
				"- summarize the student's progress\n" +
				"- identify strengths\n" +
				"- identify weaknesses\n" +
				"- analyze attendance\n" +
				"- analyze Quranic competition performance\n" +
				"- give recommendations to the teacher\n" +
				"\n" +
				"Do not invent information."
			)
				.system(
						"أنت شيخ عالم ومعلّم متمرس للقرآن الكريم في تطبيق كتّابي. " +
								"مهمتك تحليل المسار التعليمي للطالب، بما في ذلك الحضور، وتقدم حفظ القرآن الكريم، " +
								"والمشاركة في المسابقات القرآنية، ثم إعداد تقرير مهني ومفصل موجّه إلى معلّم الطالب. " +
								"يجب أن تكون جميع إجاباتك وتقاريرك باللغة العربية الفصحى فقط، " +
								"ولا تستخدم اللغة الإنجليزية أو الفرنسية بأي حال من الأحوال. " +
								"اكتب بأسلوب شيخ حكيم، فصيح، رحيم، ومتزن، مع استعمال عبارات عربية سليمة وواضحة. " +
								"حلّل المعلومات المقدمة فقط، ولا تخترع أي معلومة غير موجودة في بيانات الطالب."
				)
				.user(
						"بيانات الطالب:\n" +
								"الاسم: " + aiRapportRequestDTO.getPrenom() + "\n" +
								"النسب: " + aiRapportRequestDTO.getNom() + "\n" +
								"تاريخ الميلاد: " + aiRapportRequestDTO.getDateNaissance() + "\n\n" +

								"سجل الحضور:\n" +
								aiRapportRequestDTO.getPresences() + "\n\n" +

								"تقدم الطالب في حفظ القرآن الكريم:\n" +
								aiRapportRequestDTO.getProgressions() + "\n\n" +

								"المشاركة في المسابقات القرآنية:\n" +
								aiRapportRequestDTO.getParticipations() + "\n\n" +

								"المطلوب:\n" +
								"اكتب تقريراً شاملاً عن الطالب باللغة العربية الفصحى فقط، يتضمن:\n" +
								"1. ملخصاً عن مستوى الطالب وتطوره.\n" +
								"2. نقاط القوة.\n" +
								"3. الجوانب التي تحتاج إلى تحسين.\n" +
								"4. تحليل الحضور والمواظبة.\n" +
								"5. تحليل مستوى التقدم في حفظ القرآن الكريم.\n" +
								"6. تحليل أداء الطالب في المسابقات القرآنية.\n" +
								"7. توصيات عملية ومناسبة لمعلم الطالب.\n" +
								"8. خاتمة تربوية موجزة.\n\n" +

								"مهم جداً: اكتب التقرير كاملاً باللغة العربية الفصحى فقط، " +
								"ولا تكتب أي كلمة أو عنوان باللغة الإنجليزية أو الفرنسية."
				)
				.call()
				.entity(AiRapportResponceDTO.class);
	}
}
