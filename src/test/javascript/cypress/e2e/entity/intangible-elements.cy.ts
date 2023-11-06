import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('IntangibleElements e2e test', () => {
  const intangibleElementsPageUrl = '/intangible-elements';
  const intangibleElementsPageUrlPattern = new RegExp('/intangible-elements(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const intangibleElementsSample = { value: 'Incredible', language: 'ENGLISH' };

  let intangibleElements;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/intangible-elements+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/intangible-elements').as('postEntityRequest');
    cy.intercept('DELETE', '/api/intangible-elements/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (intangibleElements) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/intangible-elements/${intangibleElements.id}`,
      }).then(() => {
        intangibleElements = undefined;
      });
    }
  });

  it('IntangibleElements menu should load IntangibleElements page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('intangible-elements');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('IntangibleElements').should('exist');
    cy.url().should('match', intangibleElementsPageUrlPattern);
  });

  describe('IntangibleElements page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(intangibleElementsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create IntangibleElements page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/intangible-elements/new$'));
        cy.getEntityCreateUpdateHeading('IntangibleElements');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', intangibleElementsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/intangible-elements',
          body: intangibleElementsSample,
        }).then(({ body }) => {
          intangibleElements = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/intangible-elements+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/intangible-elements?page=0&size=20>; rel="last",<http://localhost/api/intangible-elements?page=0&size=20>; rel="first"',
              },
              body: [intangibleElements],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(intangibleElementsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details IntangibleElements page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('intangibleElements');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', intangibleElementsPageUrlPattern);
      });

      it('edit button click should load edit IntangibleElements page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('IntangibleElements');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', intangibleElementsPageUrlPattern);
      });

      it('edit button click should load edit IntangibleElements page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('IntangibleElements');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', intangibleElementsPageUrlPattern);
      });

      it('last delete button click should delete instance of IntangibleElements', () => {
        cy.intercept('GET', '/api/intangible-elements/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('intangibleElements').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', intangibleElementsPageUrlPattern);

        intangibleElements = undefined;
      });
    });
  });

  describe('new IntangibleElements page', () => {
    beforeEach(() => {
      cy.visit(`${intangibleElementsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('IntangibleElements');
    });

    it('should create an instance of IntangibleElements', () => {
      cy.get(`[data-cy="value"]`).type('Maryland').should('have.value', 'Maryland');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        intangibleElements = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', intangibleElementsPageUrlPattern);
    });
  });
});
