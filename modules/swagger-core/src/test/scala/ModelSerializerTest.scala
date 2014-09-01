import com.wordnik.swagger.models._
import com.wordnik.swagger.models.properties._
import com.wordnik.swagger.converter._

import com.wordnik.swagger.util.Json

import scala.collection.mutable.HashMap
import scala.collection.JavaConverters._

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.Matchers

@RunWith(classOf[JUnitRunner])
class ModelSerializerTest extends FlatSpec with Matchers {
  val m = Json.mapper()

  it should "convert a model" in {
    val pet = new ModelImpl()
    val props = new HashMap[String, Property]
    props += "intValue" -> new IntegerProperty
    props += "longValue" -> new LongProperty
    props += "dateValue" -> new DateProperty
    props += "dateTimeValue" -> new DateTimeProperty
    pet.setProperties(props.asJava)
    pet.setEnum(List("intValue", "name").asJava)

    m.writeValueAsString(pet) should be ("""{"enum":["intValue"],"properties":{"dateValue":{"type":"string","format":"date"},"longValue":{"type":"integer","format":"int64"},"dateTimeValue":{"type":"string","format":"date-time"},"intValue":{"type":"integer","format":"int32"}}}""")
  }

  it should "deserialize a model" in {
    val json = """{"enum":["intValue"],"properties":{"dateValue":{"type":"string","format":"date"},"longValue":{"type":"integer","format":"int64"},"dateTimeValue":{"type":"string","format":"date-time"},"intValue":{"type":"integer","format":"int32"}}}"""

    val p = m.readValue(json, classOf[Model])
    m.writeValueAsString(p) should equal (json)
  }
}