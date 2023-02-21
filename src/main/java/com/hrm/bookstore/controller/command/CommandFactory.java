package com.hrm.bookstore.controller.command;

import com.hrm.bookstore.controller.command.impl.ErrorCommand;
import com.hrm.bookstore.controller.command.impl.HomeCommand;
import com.hrm.bookstore.controller.command.impl.LoginCommand;
import com.hrm.bookstore.controller.command.impl.LoginFormCommand;
import com.hrm.bookstore.controller.command.impl.LogoutCommand;
import com.hrm.bookstore.controller.command.impl.book.BookCommand;
import com.hrm.bookstore.controller.command.impl.book.BooksCommand;
import com.hrm.bookstore.controller.command.impl.order.AddToCartCommand;
import com.hrm.bookstore.controller.command.impl.order.CartCommand;
import com.hrm.bookstore.controller.command.impl.order.CreateOrderCommand;
import com.hrm.bookstore.controller.command.impl.order.OrderCommand;
import com.hrm.bookstore.controller.command.impl.order.OrdersCommand;
import com.hrm.bookstore.controller.command.impl.user.CreateUserCommand;
import com.hrm.bookstore.controller.command.impl.user.CreateUserFormCommand;
import com.hrm.bookstore.controller.command.impl.user.EditUserCommand;
import com.hrm.bookstore.controller.command.impl.user.EditUserFormCommand;
import com.hrm.bookstore.controller.command.impl.user.UserCommand;
import com.hrm.bookstore.controller.command.impl.user.UsersCommand;
import com.hrm.bookstore.controller.util.PagingUtil;
import com.hrm.bookstore.data.connection.ConnectionManager;
import com.hrm.bookstore.data.connection.impl.ConnectionManagerImpl;
import com.hrm.bookstore.data.dao.BookDao;
import com.hrm.bookstore.data.dao.OrderDao;
import com.hrm.bookstore.data.dao.OrderInfoDao;
import com.hrm.bookstore.data.dao.UserDao;
import com.hrm.bookstore.data.dao.impl.BookDaoImpl;
import com.hrm.bookstore.data.dao.impl.OrderDaoImpl;
import com.hrm.bookstore.data.dao.impl.OrderInfoDaoImpl;
import com.hrm.bookstore.data.dao.impl.UserDaoImpl;
import com.hrm.bookstore.data.mapper.DataMapper;
import com.hrm.bookstore.data.mapper.DataMapperImpl;
import com.hrm.bookstore.data.repository.BookRepository;
import com.hrm.bookstore.data.repository.OrderRepository;
import com.hrm.bookstore.data.repository.UserRepository;
import com.hrm.bookstore.data.repository.impl.BookRepositoryImpl;
import com.hrm.bookstore.data.repository.impl.OrderRepositoryImpl;
import com.hrm.bookstore.data.repository.impl.UserRepositoryImpl;
import com.hrm.bookstore.service.BookService;
import com.hrm.bookstore.service.DigestService;
import com.hrm.bookstore.service.OrderService;
import com.hrm.bookstore.service.UserService;
import com.hrm.bookstore.service.impl.BookServiceImpl;
import com.hrm.bookstore.service.impl.DigestServiceImpl;
import com.hrm.bookstore.service.impl.OrderServiceImpl;
import com.hrm.bookstore.service.impl.UserServiceImpl;
import com.hrm.bookstore.util.ConfigurationManager;
import com.hrm.bookstore.util.impl.ConfigurationManagerImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.message.ParameterizedMessage;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class CommandFactory {
    private static final CommandFactory INSTANCE = new CommandFactory();
    private static final String DB_URL_KEY = "db.url";
    private static final String DB_PASSWORD_KEY = "db.password";
    private static final String DB_USER_KEY = "db.user";
    private static final String DB_DRIVER_KEY = "db.driver";
    private static final String DB_POOL_SIZE_KEY = "db.poolSize";
    private final List<Closeable> resources;

    private final Map<String, Command> commands;

    public static CommandFactory getInstance() {
        return INSTANCE;
    }

    private CommandFactory() {
        resources = new ArrayList<>();
        commands = new HashMap<>();

        //connectionManager
        ConfigurationManager configurationManager = new ConfigurationManagerImpl("/application.properties");
        String url = configurationManager.getProperty(DB_URL_KEY);
        String password = configurationManager.getProperty(DB_PASSWORD_KEY);
        String user = configurationManager.getProperty(DB_USER_KEY);
        String driver = configurationManager.getProperty(DB_DRIVER_KEY);
        ConnectionManager connectionManager = new ConnectionManagerImpl(url, password, user, driver);
        int poolSize = Integer.parseInt(configurationManager.getProperty(DB_POOL_SIZE_KEY));
        connectionManager.setPoolSize(poolSize);
        resources.add(connectionManager);

        //dao
        BookDao bookDao = new BookDaoImpl(connectionManager);
        UserDao userDao = new UserDaoImpl(connectionManager);
        OrderInfoDao orderInfoDao = new OrderInfoDaoImpl(connectionManager);
        OrderDao orderDao = new OrderDaoImpl(connectionManager);

        //repository
        DataMapper dataMapper = new DataMapperImpl();
        BookRepository bookRepository = new BookRepositoryImpl(bookDao, dataMapper);
        UserRepository userRepository = new UserRepositoryImpl(userDao, dataMapper);
        OrderRepository orderRepository = new OrderRepositoryImpl(orderDao, orderInfoDao, userDao, bookDao, dataMapper);

        //service
        BookService bookService = new BookServiceImpl(bookRepository);
        OrderService orderService = new OrderServiceImpl(orderRepository, bookRepository);
        DigestService digestService = new DigestServiceImpl();
        UserService userService = new UserServiceImpl(userRepository, digestService);

        //book
        commands.put("book", new BookCommand(bookService));
        commands.put("books", new BooksCommand(bookService, PagingUtil.INSTANCE));
        //order
        commands.put("add_to_cart", new AddToCartCommand());
        commands.put("cart", new CartCommand(orderService));
        commands.put("create_order", new CreateOrderCommand(orderService));
        commands.put("order", new OrderCommand(orderService));
        commands.put("orders", new OrdersCommand(orderService));
        //user
        commands.put("create_user", new CreateUserCommand(userService));
        commands.put("create_user_form", new CreateUserFormCommand());
        commands.put("edit_user", new EditUserCommand(userService));
        commands.put("edit_user_form", new EditUserFormCommand(userService));
        commands.put("user", new UserCommand(userService));
        commands.put("users", new UsersCommand(userService));
        //other
        commands.put("error", new ErrorCommand());
        commands.put("home", new HomeCommand());
        commands.put("login", new LoginCommand(userService));
        commands.put("login_form", new LoginFormCommand());
        commands.put("logout", new LogoutCommand());
    }

    public Command getCommand(String command) {
        Command commandInstance = commands.get(command);
        if (commandInstance == null) {
            commandInstance = commands.get("error");
        }
        return commandInstance;
    }

    public void shutdown() {
        resources.forEach(resource -> {
            try {
                resource.close();
            } catch (IOException e) {
                log.error(new ParameterizedMessage("Unable to close: {}", resource), e);
            }
        });
    }

}
