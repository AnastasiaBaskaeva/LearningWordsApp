package com.example.learningwords
// https://www.figma.com/design/4EQOcV92Gdcz8I9mV6ykky/LearnEnglishWordsApp---in-progress?node-id=177-2881&t=rUlX0TMS9n3zF7sL-0
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.learningwords.databinding.ActivityFigmaBinding

class MainActivity : ComponentActivity() {

    private var _binding: ActivityFigmaBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityFigmaBinding must not be null!")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFigmaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val trainer = LearnWordsTrainer()
        showNextQuestion(trainer)

        with(binding){
            btnContinue.setOnClickListener {
                layoutResult.isVisible = false
                markerAnswerNeutral(layoutAnswer1, tvVariantNumber1, tvVariantValue1)
                markerAnswerNeutral(layoutAnswer2, tvVariantNumber2, tvVariantValue2)
                markerAnswerNeutral(layoutAnswer3, tvVariantNumber3, tvVariantValue3)
                markerAnswerNeutral(layoutAnswer4, tvVariantNumber4, tvVariantValue4)
                showNextQuestion(trainer)
            }

            btnSkip.setOnClickListener {
                showNextQuestion(trainer)
            }
        }
    }

    private fun showNextQuestion(trainer : LearnWordsTrainer){
        val firstQuestion : Question? = trainer.getNextQuestion()
        with(binding){
            if(firstQuestion == null || firstQuestion.variants.size < 4) {
                layoutResult.isVisible = false
                layoutAnswers.isVisible = false
                tvQuestionWord.isVisible = false
                btnSkip.isVisible = true
                btnSkip.text = resources.getString(R.string.tv_completed)
            }else{
                btnSkip.isVisible = true
                tvQuestionWord.isVisible = true
                tvQuestionWord.text = firstQuestion.correctAnswer.original

                tvVariantValue1.text = firstQuestion.variants[0].translate
                tvVariantValue2.text = firstQuestion.variants[1].translate
                tvVariantValue3.text = firstQuestion.variants[2].translate
                tvVariantValue4.text = firstQuestion.variants[3].translate

                layoutAnswer1.setOnClickListener {
                    if (trainer.checkAnswer(0)){
                        markAnswerCorrect(layoutAnswer1, tvVariantNumber1, tvVariantValue1)
                        showResultMessage(true)
                    }else{
                        markAnswerWrong(layoutAnswer1, tvVariantNumber1, tvVariantValue1)
                        showResultMessage(false)
                    }
                }

                layoutAnswer2.setOnClickListener {
                    if (trainer.checkAnswer(1)){
                        markAnswerCorrect(layoutAnswer2, tvVariantNumber2, tvVariantValue2)
                        showResultMessage(true)
                    }else{
                        markAnswerWrong(layoutAnswer2, tvVariantNumber2, tvVariantValue2)
                        showResultMessage(false)
                    }
                }

                layoutAnswer3.setOnClickListener {
                    if (trainer.checkAnswer(2)){
                        markAnswerCorrect(layoutAnswer3, tvVariantNumber3, tvVariantValue3)
                        showResultMessage(true)
                    }else{
                        markAnswerWrong(layoutAnswer3, tvVariantNumber3, tvVariantValue3)
                        showResultMessage(false)
                    }
                }

                layoutAnswer4.setOnClickListener {
                    if (trainer.checkAnswer(3)){
                        markAnswerCorrect(layoutAnswer4, tvVariantNumber4, tvVariantValue4)
                        showResultMessage(true)
                    }else{
                        markAnswerWrong(layoutAnswer4, tvVariantNumber4, tvVariantValue4)
                        showResultMessage(false)
                    }
                }

            }
        }
    }

    private fun MainActivity.markAnswerCorrect(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView
    ) {
        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_containers_correct
        )
        tvVariantNumber.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_variants_correct
        )
        tvVariantNumber.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.white
            )
        )
        tvVariantValue.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.panel_green
            )
        )
        showResultMessage(true)
    }


    private fun MainActivity.markAnswerWrong(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView
    ) {
        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_containers_wrong
        )
        tvVariantNumber.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_variants_wrong
        )
        tvVariantNumber.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.white
            )
        )
        tvVariantValue.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.wrong_answer
            )
        )
        showResultMessage(false)

    }


    private fun MainActivity.markerAnswerNeutral(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView
    ) {
        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_containers
        )

        tvVariantNumber.apply {
            background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.shape_rounded_variants
            )
            setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.text_variants
                )
            )
        }

        tvVariantValue.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.text_variants
            )
        )


    }


    private fun showResultMessage(isCorrect : Boolean){
        val color : Int; val textMessage : String; val resultIconResource : Int
        if(isCorrect){
            color = ContextCompat.getColor(this, R.color.panel_green)
            textMessage = resources.getString(R.string.tv_correct)
            resultIconResource = R.drawable.ic_correct

        }else{
            color = ContextCompat.getColor(this, R.color.wrong_answer)
            textMessage = resources.getString(R.string.tv_wrong)
            resultIconResource = R.drawable.ic_wrong
        }

        with(binding){
            btnSkip.isVisible = false
            layoutResult.isVisible = true
            btnContinue.setTextColor(color)
            layoutResult.setBackgroundColor(color)
            tvResultMessage.text = textMessage
            ivResultIcon.setImageResource(resultIconResource)
        }
    }
}





